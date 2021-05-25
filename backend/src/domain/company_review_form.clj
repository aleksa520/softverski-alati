(ns domain.company_review_form
  (:require  [schema.core :as schema]
             [korma.core :refer :all]
             [korma.db :refer :all]
             [database.database :refer [db]]
             [domain.company :refer :all]))

(declare company_review_form)

(defentity company_review_form)

(schema/defschema Review
  {
   :id schema/Int
   :stars schema/Int
   :comment schema/Str
   :company Company
   })

(schema/defschema SaveReview
  {
   :stars schema/Int
   :comment schema/Str
   :company_id schema/Int
   })