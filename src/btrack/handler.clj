(ns btrack.handler
  (:require [clojure.tools.logging :as log]

            [ring.util.response :refer [response]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [buddy.core.nonce :as nonce]
            [buddy.core.codecs :as codecs]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.backends.token :refer [token-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]))

(def tokens (atom {}))

(def auth-data {:admin "secret"
                :test "secret"})

(defn random-token
  []
  (let [randomdata (nonce/random-bytes 16)]
    (codecs/bytes->hex randomdata)))

(defn ok [d] {:status 200 :body d})

(defn bad-request [d] {:status 400 :body d})

(defn init []
  (log/info "Initialising application"))

(defn shutdown []
  (log/info "Shutting down application"))

(defn home
  [request]
  (if-not (authenticated? request)
    (throw-unauthorized)
    (ok {:status "Logged" :message (str "hello logged user"
                                         (:identity request))})))

(defn login
  [request]
  (let [username (get-in request [:body :username])
        password (get-in request [:body :password])
        valid? (some-> auth-data
                       (get (keyword username))
                       (= password))]
    (if valid?
      (let [token (random-token)]
        (swap! tokens assoc (keyword token) (keyword username))
        (ok {:token token}))
      (bad-request {:message "wrong auth data"}))))

(defn my-authfn
  [req token]
  (when-let [user (get @tokens (keyword token))]
    (user)))

(def backend (token-backend {:authfn my-authfn}))

(defroutes app-routes
           (GET  "/" [] home)
           (POST  "/login" [] login))

(def app
  (-> app-routes
      (wrap-authentication backend)
      (wrap-authorization backend)
      (wrap-json-response {:pretty false})
      (wrap-json-body {:keywords? true :bigdecimals? true})))

