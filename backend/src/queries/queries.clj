(ns queries.queries
  (:require [korma.core :refer :all]
            [korma.db :refer :all]
            [schema.core :as schema]
            [clojure.core :as core]
            [domain.industry :refer :all]
            [domain.company :refer :all]
            [domain.users :refer :all]
            [domain.company_review_form :refer :all])
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

;; company

(defn get-companies []
  (select company
          (with industry)
          (fields :company.id :company.fullName :company.numberOfEmployees :industry_id :industry.industryName)
          ))


(defn get-company-by-name [fullName]
  (first (select company (fields :id :fullName :numberOfEmployees)
          (where {:fullName fullName}))))


(defn get-company [id]
  (first (select company (fields :id :fullName :numberOfEmployees)
          (where {:id id}))))

(defn delete-company [username id]
  (def existingCompany (get-company id))
  (if (= username "admin")
    (if existingCompany
           (delete company (where {:id id}))
           "Company does not exist")
  "Please provide credentials!"))


(defn add-company [username fullName numberOfEmployees industry_id]
  (def existingCompany (get-company-by-name fullName))
  (def existingIndustry (get-industry industry_id))
  (if (= username "admin")
  (if existingCompany
    "Company already exist!"
    (if existingIndustry
        ((def insertCompany
           (insert company (values
                             {:fullName fullName
                              :industry_id industry_id
                              :numberOfEmployees numberOfEmployees
                              })))
         (def insertedCompany (get insertCompany :generated_key))
         (get-company insertedCompany))
        "Industry does not exist!"))
  "Please provide credentials!"))

(defn update-company [username id fullName numberOfEmployees industry_id]
  (def existingCompany (get-company id))
  (def existingIndustry (get-industry industry_id))
  (if (= username "admin")
    (if existingCompany
      (if existingIndustry
         (update company (set-fields
                    {:fullName fullName
                     :numberOfEmployees numberOfEmployees
                     :industry_id industry_id})
          (where {:id id}))
         "Industry does not exist!")
         "Company does not exist!")
  "Please provide credentials!"))                

;; company review

(defn add-review [stars comment company_id]
  (insert company_review_form (values {
                                       :stars stars
                                       :comment comment
                                       :company_id company_id
                                       })))