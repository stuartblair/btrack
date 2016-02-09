(ns btrack.handler
  (:require [clojure.tools.logging :as log]

            [ring.util.response :as response]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [compojure.core :refer :all]
            [compojure.route :as route]))

(defroutes app-routes
           (GET  "/" [] "Hello there!")
           (GET  "/users" [] "All the users right here!")
           (route/not-found {:status 404 :body "Not Found"}))


(def app
  ;; Disable anti-forgery as it interferes with Connect POSTs
  (let [connect-defaults (-> site-defaults
                             (assoc-in [:security :anti-forgery] false)
                             (assoc-in [:security :frame-options] false)) ]

    (-> app-routes
        (wrap-defaults connect-defaults))))

(defn init []
  (log/info "Initialising application"))

(defn shutdown []
  (log/info "Shutting down application"))