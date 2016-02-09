(ns btrack.core
  (:gen-class)
  (:require [clojure.tools.logging :as log]

            [btrack.handler :refer [app init shutdown]]

            [immutant.web :as web]
            [immutant.web.middleware :refer [wrap-development]]

            [environ.core :refer [env]]))


(defonce server (atom nil))
(defonce port (env :port 3000))

(defn start-server [port]
  (init)
  (reset! server (web/run (wrap-development app) :port port)))

(defn stop-server []
  (when @server
    (shutdown)
    (web/stop @server)
    (reset! server nil)))

(defn start-app []
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop-server))
  (start-server port)
  (log/info "server started on port:" (:port @server)))

(defn -main [& args]
  (start-app))