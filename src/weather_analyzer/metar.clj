(ns weather-analyzer.metar
  "Functions to interact with metar aviation data"
  (require
   [clj-http.client :as client]
   [clojure.data.xml :as xml]))

(def low-cloud-max-ft
  6500)

(def mid-cloud-max-ft
  23000)

; (def sky-cover->percentage
;   {"CLR" 0
;    "SKC" 5
;    "FEW" 25
;    "SCT" 50
;    "BKN" 87
;    "OVC" 100})

(def default-airport
  "KPHL")

(def metar-url-format
  "https://aviationweather.gov/adds/dataserver_current/httpparam?dataSource=metars&requestType=retrieve&stationString=%s&hoursBeforeNow=1")

(defn- get-current-observation
  "Get the current cloud observations from the given airport"
  [airport]
  (let [aviation-data-xml (client/get (format metar-url-format airport))
        aviation-data (xml/parse-str (:body aviation-data-xml))]
    (:content (first (:content (some #(when (= (:tag %) :data) %) (:content aviation-data)))))))

(defn- cloud-observations->cloud-data
  "Categorize the cloud observations as low, medium, high"
  [cloud-observations]
  (let [cloud-conditions (map :attrs cloud-observations)]
    (for [condition cloud-conditions
          :let [cloud-height (Integer. (:cloud_base_ft_agl condition))]]
      (if (<= cloud-height low-cloud-max-ft)
        :low
        (if (<= cloud-height mid-cloud-max-ft)
          :mid
          :high)))))

(defn get-current-cloud-data
  "Get the current cloud data from metars data and categorize into cloud observations"
  []
  (let [observation (get-current-observation default-airport)
        cloud-observations (filter #(= (:tag %) :sky_condition) observation)]
    (distinct (cloud-observations->cloud-data cloud-observations))))
