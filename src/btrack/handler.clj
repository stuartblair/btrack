(ns btrack.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            ;[compojure.handler :as handler]
            [ring.util.response :refer [response]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
           (route/not-found
             (response {:message "Page not found"})))

(defn wrap-log-request [handler]
  (fn [req]
    (println req)
    (handler req)))

(def app
  (-> app-routes
      wrap-log-request
      wrap-json-response
      wrap-json-body))

