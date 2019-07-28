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
  (zipmap words (lengths words)))

(defn word-by-syllable
  [length pairs]
  (filter (comp #{length} pairs) (keys pairs)))

(def get-word-list
  (-> (http/get "https://api.noopschallenge.com/wordbot?count=1000") :body))

(defn decoder
  [word-list]
  (parse-string word-list))

(defn get-words
  [word-list]
  (get (decoder word-list) "words"))

(defn paired [word-list] (pair (get-words word-list)))

(defn get-word
  [length pairs]
  (rand-nth (word-by-syllable length pairs)))

(defn l1w1 [word-list] (get-word 2 (paired word-list)))

(defn l1w2 [word-list] (get-word 3 (paired word-list)))

(defn l2w1 [word-list] (get-word 3 (paired word-list)))

(defn l2w2 [word-list] (get-word 1 (paired word-list)))

(defn l2w3 [word-list] (get-word 3 (paired word-list)))

(defn l3w1 [word-list] (get-word 1 (paired word-list)))

(defn l3w2 [word-list] (get-word 4 (paired word-list)))

(defn first-line [word-list] [(l1w1 word-list) (l1w2 word-list)])

(defn second-line [word-list] [(l2w1 word-list) (l2w2 word-list) (l2w3 word-list)])

(defn third-line [word-list] [(l3w1 word-list) (l3w2 word-list)])

(defn line-generator
  [line]
  (if line 
    (clojure.string/join " " line)
    "fuck"))

(def wrap-params p/wrap-params)
