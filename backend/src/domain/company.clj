(ns domain.company
  (:require  [schema.core :as schema]
             [korma.core :refer :all]
             [korma.db :refer :all]
             [database.database :refer [db]]
             [domain.industry :refer :all]))


(declare company)

(defentity company
           (pk :id)
           (belongs-to industry))


(schema/defschema SaveOrUpdateCompany
  {
   :fullName schema/Str
   :numberOfEmployees schema/Str
   :industry_id schema/Int
   })


(schema/defschema Company
  {
   :id schema/Int
   :fullName schema/Str
   :numberOfEmployees schema/Str
   :industry_id schema/Int
   })