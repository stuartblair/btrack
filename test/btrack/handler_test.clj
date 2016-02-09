(ns btrack.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [btrack.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))))

  (testing "users"
    (let [response (app (mock/request :get "/users"))]
      (is (= (:status response) 200))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
