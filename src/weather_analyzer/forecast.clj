(ns weather-analyzer.forecast
  (require
   [clojure.set :as set]
   [clojure.string :as string]
   [clj-time.core :as time]
   [weather-analyzer.forecast-chart :as chart]
   [weather-analyzer.metar :as metar]
   [weather-analyzer.weather-underground :as weather-underground]))

(def default-if-not-present
  "True to consider a match when condition is not present in the forecast chart"
  true)

(defn is-precipitating?
  "Is there currently precipitation"
  [precip-metric]
  (> (Integer. (string/trim precip-metric)) 0))

(defn- pressure-in-range?
  "Is the current pressure within the pressure range of the forecast"
  [conditions forecast]
  (if-let [forecast-range (:pressure forecast)]
    (let [pressure-mb (:pressure-mb conditions)]
      (and (or (nil? (:mb-max forecast-range))
               (<= pressure-mb (:mb-max forecast-range)))
           (or (nil? (:mb-min forecast-range))
               (>= pressure-mb (:mb-min forecast-range)))))
    default-if-not-present))

(defn- includes-pressure-trend?
  "Does the current pressure trend match the forecast pressure trend"
  [conditions forecast]
  (if-let [trends (:pressure-trend forecast)]
    (contains? (set trends) (:pressure-trend conditions))
    default-if-not-present))

(defn- is-in-season?
  "Does the current season match the forecast season"
  [conditions forecast]
  (if-let [seasons (:seasons forecast)]
    (contains? (set seasons) (:season conditions))
    default-if-not-present))

(defn- wind-direction-matches?
  "Does the current wind direction match the forecast wind direction"
  [conditions forecast]
  (if-let [wind-directions (:wind-direction forecast)]
    (contains? (set wind-directions) (:wind-direction conditions))
    default-if-not-present))

(defn- cloud-level-matches?
  "Do the current cloud levels match the forecast cloud levels. Current cloud
  levels are a list."
  [conditions forecast]
  (if-let [cloud-levels (:clouds forecast)]
    (not-empty (set/intersection (set cloud-levels) (set (:heights (:cloud-levels conditions)))))
    default-if-not-present))

(def analyzer-functions
  "Functions to use to compare the current conditions against a forecast"
  [pressure-in-range?
   includes-pressure-trend?
   ; is-in-season?
   wind-direction-matches?
   cloud-level-matches?])

(defn analyze-matches
  "Loop through the defined analyzer functions to determine if the conditions match"
  [updated-conditions forecast]
  (for [analyzer-function analyzer-functions]
    (analyzer-function updated-conditions forecast)))

(defn matches-conditions-count
  "Get a count of matching conditions for the given forecast based on the updated
  conditions"
  [updated-conditions forecast]
  (let [matches (analyze-matches updated-conditions forecast)]
    (count (filter true? matches))))

(defn adjust-cloud-data
  "Adjust cloud data based on whether it is overcast or precipitating. If overcast
  or precipitating, we don't need to know low/medium/high"
  [current-conditions cloud-levels]
  (if (is-precipitating? (:precip_1hr_metric current-conditions))
    {:heights [:precipitation]
     :sky-cover 100}
    (if-let [clouds (chart/weather-to-cloud-level (:weather current-conditions))]
      clouds
      (if (empty? cloud-levels)
        {:heights [:precipitation]
         :sky-cover 100}
        cloud-levels))))

(defn- generate-forecast-string
  "Generate a forecast string from the forecast codes"
  [data]
  (string/join ", "
   (for [forecast (:forecast data)]
     (chart/forecast-codes forecast))))

(defn forecast
  "Analyze the current conditions against the defined forecasts. Sort them by highest
  match and generate the forecast string."
  [updated-conditions]
  (let [matched-conditions
        (map #(assoc % :match (matches-conditions-count updated-conditions %)) chart/forecasts)
        forecasts (sort-by :match (comp - compare) matched-conditions)]
    (generate-forecast-string (first forecasts))))

(defn- output-conditions
  "Print select current conditions"
  [updated-conditions]
  (println (select-keys updated-conditions
                        [:season
                         :pressure-mb
                         :pressure-trend
                         :wind-direction
                         :cloud-levels])))

(defn forecast-by-station-id
  "Generate a forecast for the given station id. Get the current conditions from
  weather underground, update the current conditions so they can be analyzed, and
  generate the forecast"
  [station-id]
  (let [current-conditions (weather-underground/query-current-conditions station-id)
        updated-conditions (-> current-conditions
                               (assoc :season (get chart/seasons (time/month (time/now))))
                               (assoc :pressure-mb (Integer. (:pressure_mb current-conditions)))
                               (assoc :pressure-trend (get chart/pressure-trends (:pressure_trend current-conditions) :steady))
                               (assoc :wind-direction (get chart/wind-directions (:wind_dir current-conditions)))
                               (merge {:cloud-levels (adjust-cloud-data current-conditions (metar/get-current-cloud-data))}))]
    ;(def updated-conditions updated-conditions)
    ;(output-conditions updated-conditions)
    (forecast updated-conditions)))

(comment
  (forecast-by-station-id "KPASPRIN50"))
