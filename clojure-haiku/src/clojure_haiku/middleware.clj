(ns clojure-haiku.middleware
  (:require
    [clj-http.client :as http]
    [cheshire.core :refer :all]
    [ring.middleware.params :as p]))

(defn user-replace
  [userRegex replacer string]
  (clojure.string/replace string userRegex replacer))

(defn basic-count
  [word]
  user-replace #"(?:[^laeiouy]es|ed|[^laeiouy]e)$" "" word)

(defn y?
  [word]
  user-replace #"^y" "" word)

(defn match-syllables
  [word]
  re-find #"[aeiouy]{1,2}" word)

(defn match
  [word]
  re-find (match-syllables (y? (basic-count word))))

(defn matches
  [words]
  (map match words))

(defn get-lengths
  [matches]
  (map alength matches))

(defn lengths
  [words]
  get-lengths (matches words))

(defn pair
  [words]
  (map words (lengths words)))

(defn syllables-equal?
  [length pairs]
  length == (map (get (keys pairs))))

(defn user-shuffle
  [words]
  (shuffle words))

(defn syllables?
  [syllables pairs]
  (filter (syllables-equal? syllables pairs)))

(defn word-by-syllable
  [syllables words]
  (first (syllables? syllables (pair (shuffle words)))))

(def get-word-list
  (-> (http/get "https://api.noopschallenge.com/wordbot?count=1000") :body))

(defn decoder
  [word-list]
  parse-string word-list)

(def get-words
  (get (decoder get-word-list) "words"))

(defn first-line
  [words]
  (vector (word-by-syllable 2 words) 
          (word-by-syllable 3 words)))

(defn second-line
  [words]
  (vector (word-by-syllable 2 words)
          (word-by-syllable 1 words)
          (word-by-syllable 4 words)))

(defn third-line
   [words]
   (vector (word-by-syllable 4 words)
           (word-by-syllable 1 words)))

(defn get-word
  [word-map]
  (if (word-map not= nil) 
    (get word-map (first (keys word-map))) ("fuck")))

(defn line-generator
  [lines words]
  (if (words not= nil) 
    (clojure.string/join " " (map get-word lines words))
    ("fuck")))

(def wrap-params p/wrap-params)
