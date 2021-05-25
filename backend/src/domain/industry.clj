(ns domain.industry
  (:require  [schema.core :as schema]
             [korma.core :refer :all]
             [korma.db :refer :all]
             [database.database :refer [db]]))


(declare industry)

(defentity industry
           (pk :id))


(schema/defschema SaveOrUpdateIndustry
  {
   :name schema/Str
   })