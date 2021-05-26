(ns handler.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [queries.queries :as qrs]
            [schema.core :as schema]
            [domain.industry :refer :all]
            [domain.company :refer :all]))
(def auth)

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "API"
                    :description "Companies Api"}
             :tags [{:name "api"}
                    {:name "industry"}]}}}

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
        )))
