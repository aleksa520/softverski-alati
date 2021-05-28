(ns handler.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [queries.queries :as qrs]
            [schema.core :as schema]
            [domain.industry :refer :all]
            [domain.company :refer :all]
            [domain.users :refer :all]))
(def auth)

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "API"
                    :description "Companies Api"}
             :tags [{:name "api"}
                    {:name "industry"}
                    {:name "login"}
                    {:name "companies"}]}}}

    (context "/api" []
      :tags ["api"]
      (context "/industry" []
        :tags ["industry"]
        (GET "/" []
        :summary "Get all industries"
        (ok (qrs/get-industries)))
        (GET "/:industry_id" []
          :summary "Get companies for particular industry"
          :path-params [industry_id :- schema/Num]
          (ok (qrs/get-companies-by-industry industry_id))))
        
    (context "/login" []
      :tags ["login"]
        (POST "/" []
          :summary "Admin login"
          :body [admin AdminLogin]
          (let [{:keys [username password]} admin]
            (ok (qrs/login-admin username password)))))
                   
            (context "/companies" []
              :tags ["companies"]
              (GET "/" []
                :summary "Get all companies"
                (ok (qrs/get-companies)))
              (GET "/:id" []
                :summary "Get company by id"
                :path-params [id :- schema/Num]
                (ok (qrs/get-company id)))
              (POST "/" []
                :header-params [username :- schema/Str]
                :summary "Add new company"
                :body [company-data SaveOrUpdateCompany]
                (let [{:keys [fullName numberOfEmployees industry_id]} company-data]
                  (ok (qrs/add-company username fullName numberOfEmployees industry_id))))
              (PUT "/:id" [id]
                :header-params [username :- schema/Str]
                :summary "Update company"
                :body [company-data SaveOrUpdateCompany]
                (let [{:keys [fullName numberOfEmployees industry_id]} company-data]
                  (ok {:response (qrs/update-company username id fullName numberOfEmployees industry_id)})))
              (DELETE "/:id" []
                :header-params [username :- schema/Str]
                :summary "Delete company"
                :path-params [id :- schema/Num]
                (ok (qrs/delete-company username id))))                

          )))