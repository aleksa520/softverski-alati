(ns domain.users
  (:require  [schema.core :as schema]
             [korma.core :refer :all]
             [korma.db :refer :all]
             [database.database :refer [db]]))

(declare users)

(defentity users)

(schema/defschema Admin
  {
   :id schema/Int
   :username schema/Str
   :password schema/Str
   })

(schema/defschema AdminLogin
  {
   :username schema/Str
   :password schema/Str
   })



