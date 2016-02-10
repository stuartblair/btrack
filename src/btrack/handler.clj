(ns btrack.handler
  (:require [clojure.tools.logging :as log]

            [ring.util.response :refer [response]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.backends.httpbasic :refer [http-basic-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]))

(def authdata {:admin "secret"
               :test "secret"})

(defn init []
  (log/info "Initialising application"))

(defn shutdown []
  (log/info "Shutting down application"))

(defn handle-users
  [request]
  (if-not (authenticated? request)
    (throw-unauthorized)
    (response (format "Hello %s" (:identity request)))))

(defn my-authfn
  [req {:keys [username password]}]
  (when-let [user-password (get authdata (keyword username))]
    (when (= password user-password)
      (keyword username))))

(def backend (http-basic-backend {:realm "MyApi" :authfn my-authfn}))

(defroutes app-routes
           (GET  "/" [] "Hello there!")
           (GET  "/users" [] handle-users)
           (route/not-found {:status 404 :body "Not Found"}))

(def app
  (-> app-routes
      (wrap-authentication backend)
      (wrap-authorization backend)))

