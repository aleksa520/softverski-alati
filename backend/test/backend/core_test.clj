(ns backend.core-test
  (:require [clojure.test :refer :all]
            [handler.handler :refer :all]
            [queries.queries :as q]
            [cheshire.core :as cheshire]
            [ring.mock.request :as mock]
            [ring.util.http-status :refer :all]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(deftest a-test
  (testing "Test GET request to /api/industry"
    (let [response (app (-> (mock/request :get  "/api/industry")))
          body  (parse-body (:body response))]
      (is (= (:status response) 200))
     )))

(deftest delete-company
  (testing "Delete company no credentials"
    (is (= "Please provide credentials!" (q/delete-company "" 999))))

  (testing "Delete company that doesn't exist"
    (is (= "Company does not exist" (q/delete-company "admin" 999)))))

(deftest create-company
  (testing "Retrieve company"
  (let [company (q/add-company "admin" "Krusevac" "134" 1)
     found-company (q/get-company (company :id))]
    (is (= "Krusevac" (found-company :fullName)))
      )))

(deftest update-company
  (testing "Updates existing company"
    (let [company-orig (q/add-company "admin" "Palic" "134" 1)
          company-id (company-orig :id)]
      (q/update-company "admin" company-id "Kursumlija" "134" 1)
      (is (= "Kursumlija" (:fullName (q/get-company company-id)))))))

(deftest testing-routes
  (testing "not-found route"
  (let [response (app (-> (mock/request :get "/api/bogus-route")))]
    (is (not (= nil 400)))))
  (testing "companies endpoint"
    (let [response (app (-> (mock/request :get "/api/companies")))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8")))))