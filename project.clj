(defproject btrack "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [compojure "1.4.0"]
                 [ring "1.4.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-mock "0.3.0"]
                 [org.immutant/web "2.1.2"]
                 [environ "1.0.1"]
                 [buddy/buddy-auth "0.9.0"]
                 ]
  :plugins [
            [lein-ancient "0.6.8"]
            [com.jakemccrary/lein-test-refresh "0.12.0"]]
  :main ^:skip-aot btrack.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})