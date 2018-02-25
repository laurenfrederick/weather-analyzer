(ns weather-analyzer.forecast-chart
  "Defs related to the forecast")

(def seasons
  {1 :winter
   2 :winter
   3 :spring
   4 :spring
   5 :spring
   6 :summer
   7 :summer
   8 :summer
   9 :fall
   10 :fall
   11 :fall
   12 :winter})

(def forecasts
  [{:pressure {:mb-min 1023}
    :seasons [:winter]
    :forecast [:clear :same-cold]
    :clouds [:clear :high]}
   {:pressure {:mb-min 1023}
    :seasons [:spring :summer :fall]
    :forecast [:clear :same-temp]
    :clouds [:clear :high]}
   {:pressure {:mb-min 1016 :mb-max 1022}
    :pressure-trend [:rising :steady]
    :wind-direction [:SW :W :NW :N]
    :clouds [:clear :high]
    :forecast [:clear :same-temp]}
   {:pressure {:mb-min 1016 :mb-max 1022}
    :pressure-trend [:falling :steady]
    :wind-direction [:SW :S :SE]
    :clouds [:clear :high]
    :forecast [:clear :increasing-clouds :rising-temps :precip-24]}
   {:pressure {:mb-min 1016 :mb-max 1022}
    :pressure-trend [:falling]
    :wind-direction [:SW :S :SE]
    :clouds [:middle :low]
    :forecast [:rising-temps :precip-12]}
   {:pressure {:mb-min 1016 :mb-max 1022}
    :pressure-trend [:falling]
    :wind-direction [:E :NE]
    :clouds [:middle :low]
    :forecast [:same-cold :precip-12]}
   {:pressure {:mb-min 1016 :mb-max 1022}
    :pressure-trend [:falling :steady]
    :wind-direction [:E :NE]
    :clouds [:clear :high]
    :forecast [:increasing-clouds :precip-24 :same-cold]}
   {:pressure {:mb-min 1009 :mb-max 1015}
    :pressure-trend [:rising]
    :wind-direction [:SW :W :NW :N]
    :clouds [:clear]
    :forecast [:clear :same-cold]}
   {:pressure {:mb-min 1009 :mb-max 1015}
    :pressure-trend [:rising]
    :wind-direction [:SW :W :NW :N]
    :clouds [:overcast]
    :forecast [:clearing :colder-temps]}
   {:pressure {:mb-min 1009 :mb-max 1015}
    :pressure-trend [:rising]
    :wind-direction [:SW :W :NW :N]
    :clouds [:overcast :precipitation]
    :forecast [:clearing :colder-temps :precip-ending-6]}
   {:pressure {:mb-min 1009 :mb-max 1015}
    :pressure-trend [:falling]
    :clouds [:clear]
    :forecast [:increasing-clouds :rising-temps]}
   {:pressure {:mb-min 1009 :mb-max 1015}
    :pressure-trend [:falling :steady]
    :wind-direction [:SW :S :SE]
    :clouds [:high]
    :forecast [:increasing-clouds :rising-temps :precip-24]}
   {:pressure {:mb-min 1009 :mb-max 1015}
    :pressure-trend [:falling]
    :wind-direction [:SW :S :SE]
    :clouds [:middle :low]
    :forecast [:increasing-clouds :rising-temps :precip-8]}
   {:pressure {:mb-min 1009 :mb-max 1015}
    :pressure-trend [:falling]
    :wind-direction [:E :NE]
    :clouds [:middle :low]
    :forecast [:precip-8 :windy :same-cold]}
   {:pressure {:mb-min 1009 :mb-max 1015}
    :pressure-trend [:falling]
    :wind-direction [:SE :E :NE]
    :clouds [:precipitation]
    :forecast [:precipitation]}
   {:pressure {:mb-min 1009 :mb-max 1015}
    :pressure-trend [:falling]
    :wind-direction [:S :SW]
    :clouds [:precipitation]
    :forecast [:precip-ending-12 :wind-shift]}
   {:pressure {:mb-max 1008}
    :pressure-trend [:rising]
    :wind-direction [:SW :W :NW :N]
    :clouds [:clear]
    :forecast [:clear :windy]}
   {:pressure {:mb-max 1008}
    :pressure-trend [:rising]
    :wind-direction [:SW :W :NW :N]
    :clouds [:overcast]
    :forecast [:clearing :windy :colder-temps]}
   {:pressure {:mb-max 1008}
    :pressure-trend [:rising]
    :wind-direction [:SW :W :NW :N]
    :clouds [:precipitation]
    :forecast [:precip-ending-6 :windy :colder-temps]}
   {:pressure {:mb-max 1008}
    :pressure-trend [:rising]
    :wind-direction [:NE]
    :clouds [:overcast]
    :forecast [:overcast :windy :wind-shift :same-cold]}
   {:pressure {:mb-max 1008}
    :pressure-trend [:rising]
    :wind-direction [:NE]
    :clouds [:precipitation]
    :forecast [:precip-ending-6 :windy :wind-shift :same-cold]}
   {:pressure {:mb-max 1008}
    :pressure-trend [:rising :steady]
    :wind-direction [:SW :S :SE]
    :clouds [:clear]
    :forecast [:increasing-clouds :precip-12 :precip-heavy :windy :same-warm]}
   {:pressure {:mb-max 1008}
    :pressure-trend [:falling]
    :wind-direction [:SW :S :SE]
    :clouds [:overcast]
    :forecast [:precip-8 :precip-heavy :windy :wind-shift]}
   {:pressure {:mb-max 1008}
    :pressure-trend [:falling]
    :wind-direction [:SW :S :SE]
    :clouds [:precipitation]
    :forecast [:precip-heavy :precip-ending-12 :windy :wind-shift :colder-temps]}
   {:pressure {:mb-max 1008}
    :pressure-trend [:falling]
    :wind-direction [:N]
    :clouds [:overcast]
    :forecast [:overcast :same-cold]}
   {:pressure {:mb-max 1008}
    :pressure-trend [:falling :steady]
    :wind-direction [:E :NE]
    :clouds [:overcast]
    :forecast [:precip-8 :windy :same-cold]}
   {:pressure {:mb-max 1008}
    :pressure-trend [:falling]
    :wind-direction [:E :NE]
    :clouds [:precipitation]
    :forecast [:precip-heavy :precipitation :windy :wind-shift]}])

(def weather-to-cloud-level
  {"Overcast" :overcast
   "Clear" :clear})

(def forecast-codes
 {:clear "Clear or scattered clouds"
  :clearing "Clearing"
  :increasing-clouds "Increasing clouds"
  :overcast "Continued overcast"
  :windy "Windy"
  :wind-shift "Possible wind shift to W, NW, or N"
  :same-temp "Little temperature change"
  :same-cold "Continued cool or cold"
  :same-warm "Continued mild or warm"
  :rising-temps "Slowly rising temperatures"
  :colder-temps "Turning colder"
  :precipitation "Precipitation continuing"
  :precip-8 "Precipitation possible within 8 hours"
  :precip-12 "Precipitation possible within 12 hours"
  :precip-24 "Precipitation possible within 24 hours"
  :precip-heavy "Possible period of heavy precipitation"
  :precip-ending-6 "Precipitation ending within 6 hours"
  :precip-ending-12 "Precipitation ending within 12 hours"})

(def pressure-trends
 {"0" :steady
  "+" :rising
  "-" :falling})

(def wind-directions
 {"S"   :S
  "South" :S
  "SE"  :SE
  "ESE" :SE
  "SSE" :SE
  "SW"  :SW
  "WSW" :SW
  "SSW" :SW
  "N"   :N
  "North" :N
  "NE"  :NE
  "NNE" :NE
  "ENE" :NE
  "NW"  :NW
  "NNW" :NW
  "WNW" :NW
  "W"   :W
  "West" :W
  "E"   :E
  "East" :E})
