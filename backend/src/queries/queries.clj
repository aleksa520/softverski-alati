(ns queries.queries
  (:require [korma.core :refer :all]
            [korma.db :refer :all]
            [schema.core :as schema]
            [clojure.core :as core]
            [domain.industry :refer :all]
            [domain.company :refer :all]
            [domain.users :refer :all])
  (:import (java.security MessageDigest)))

(defn md5 [^String s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        raw (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))

;; industry

(defn get-industry [industry_id]
  (first (select industry (fields :id :industryName)
                 (where {:id industry_id}))))

(defn get-industries []
  (select industry))

(defn get-companies-by-industry [industry_id]
  (select company (fields :id :fullName :numberOfEmployees)
                (where {:industry_id industry_id})))

;; admin

(defn login-admin [username password]

  (first (select users
                 (fields :username)
                 (where {:username username :password (md5 password)}))))