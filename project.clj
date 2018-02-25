(defproject weather-analyzer "0.1.0-SNAPSHOT"
  :description "Retrieve and analyze weather station data"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [proto-repl "0.3.1"]
                 [environ "1.1.0"]
                 [clj-http "3.7.0"]
                 [cheshire "5.8.0"]
                 [clj-time "0.14.2"]
                 [org.clojure/data.xml "0.0.8"]]
  :plugins [[lein-environ "1.1.0"]]
  :main ^:skip-aot weather-analyzer.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
