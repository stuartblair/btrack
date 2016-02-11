(ns btrack.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [btrack.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= 401 (:status response)))))

  (testing "login"
    (let [response (app (mock/request :post "/login" {}))]
      (is (= 400 (:status response))))))
