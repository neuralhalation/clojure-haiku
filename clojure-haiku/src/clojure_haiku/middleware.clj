(ns clojure-haiku.middleware
  (:require
    [clj-http.client :as http]
    [cheshire.core :refer :all]
    [ring.middleware.params :as p]))


(defn basic-count
  [word]
  (clojure.string/replace word #"(?:[^laeiouy]es|ed|[^laeiouy]e)$" ""))


(defn y?
  [word]
  (clojure.string/replace word #"^y" ""))

(defn match-syllables
  [word]
  (re-seq #"[aeiouy]{1,2}" word))

(defn match
  [word]
  (match-syllables (y? (basic-count word))))

(defn matches
  [words]
  (map match words))

(defn get-lengths
  [matches]
  (map count matches))

(defn lengths
  [words]
  (get-lengths (matches words)))

(defn pair
  [words]
  (zipmap words (words lengths)))

(defn word-by-syllable
  [length pairs]
  (filter (comp #{length} pairs) (keys pairs)))

(def get-word-list
  (-> (http/get "https://api.noopschallenge.com/wordbot?count=1000") :body))

(defn decoder
  [word-list]
  (parse-string word-list))

(def get-words
  (get (decoder get-word-list) "words"))

(def shuffled (shuffle get-words))

(def paired (pair shuffled))

(def first-line
  (vector (word-by-syllable 2 paired) 
          (word-by-syllable 3 paired)))

(def second-line
  (vector (word-by-syllable 2 paired)
          (word-by-syllable 1 paired)
          (word-by-syllable 4 paired)))

(def third-line
   (vector (word-by-syllable 4 paired)
           (word-by-syllable 1 paired)))

(defn line-generator 
  [line-vector]
  (if (line-vector not= nil)
    (clojure.string/join " " line-vector)
    ("fuck")))

(def wrap-params p/wrap-params)
