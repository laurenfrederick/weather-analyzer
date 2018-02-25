(ns weather-analyzer.weather-underground
  "Functions to interact with the weather underground api"
  (require
   [cheshire.core :as json]
   [clj-http.client :as client]
   [clojure.string :as string]
   [environ.core :as environ]))

(def weather-underground-key
  (environ/env :weather-underground-api-key))

(def conditions-url-format
  "http://api.wunderground.com/api/%s/conditions/q/pws:%s.json")

(defn query-current-conditions
  "Get the current conditions for the station id"
  [station-id]
  (let [current-station-conditions
        (client/get
         (format conditions-url-format weather-underground-key station-id))]
    (:current_observation (json/parse-string (:body current-station-conditions) true))))


(comment
  (query-current-conditions "KPASPRIN50"))

  
