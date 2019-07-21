(ns clojure-haiku.views
  (:use hiccup.page hiccup.element hiccup.form)
  (:require [clojure-haiku.middleware :as mid :refer [first-line second-line third-line line-generator get-words]]))

(def inspiration-button
  (html5 [:input :on-click get-words :text "inspire me more" ]))

(defn main [words]
  (html5
    [:html
      [:head]
      [:body
        [:div {:class "container"}
          [:h2 "shitty haikus"]
          (if (words not= nil)
            ([:p (line-generator first-line words)]
             [:p (line-generator second-line words)]
             [:p (line-generator third-line words)]
             inspiration-button)
            ([:p "get inspired"]
             inspiration-button))]]]))

