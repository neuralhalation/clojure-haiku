(ns clojure-haiku.views
  (:use [hiccup.page] [hiccup.element] [hiccup.form])
  (:require [clojure-haiku.middleware :as mid :refer [first-line second-line third-line line-generator]]))

(defn footer
  []
  [:div
    (link-to "/" "back home")])

(defn main
  []
  [:div {:id "content"}
    [:h1 {:class "text-success"} "Backwoods Research Group"]
    [:h2 "The Clojure Bots"]
    [:br]
    [:h5 (link-to "/random-haiku" "the random haiku bot")]
    [:p "A bot that generates random haikus pulling from " [:a {:href "http://noopschallenge.com/challenges/wordbot"} "noopschallenge.com"] " and using a regular expression to do syllable matching."]])

(defn random-haiku
  [word-list]
  [:div {:id "content"}
    [:h1 {:class "text-success"} "shitty random haikus"]
    [:p (line-generator (first-line word-list))]
    [:p (line-generator (second-line word-list))]
    [:p (line-generator (third-line word-list))]
    (link-to {:class "btn btn-primary"} "/random-haiku" "inspire me more")
    (footer)])

; TODO: Markov Basho bot view

(defn not-found
  []
  [:div
    [:h1 {:class "info-warning"} "Page Not Found"]
    [:p "four oh four, you say / request something different / nothing is here now"]
    (link-to {:class "btn btn-primary"} "/" "to home")])
