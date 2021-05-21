(ns database.database
  (:require [korma.db :as korma]))


(def db-connection (korma/mysql {:classname   "com.mysql.cj.jdbc.Driver"
                                 :subprotocol "mysql"
                                 :user        "root"
                                 :password    "admin"
                                 :subname     "//localhost:3306/companies"}))

(korma/defdb db db-connection)
