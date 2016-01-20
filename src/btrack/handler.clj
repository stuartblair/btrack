(ns btrack.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (route/not-found "{\"message\":\"Page not found\"}"))


(defn wrap-log-request [handler]
  (fn [req]
    (println req)
    (handler req)))

(def app 
  (-> app-routes
      wrap-json-response
      wrap-json-body))

