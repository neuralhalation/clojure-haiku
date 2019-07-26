(ns clojure-haiku.views
  (:use hiccup.page hiccup.element hiccup.form)
  (:require [clojure-haiku.middleware :as mid :refer [first-line second-line third-line line-generator get-words]]))


(defn main [words]
  (html5
    [:html
      [:head]
      [:body
        [:div {:class "container"}
          [:h2 "shitty haikus"]
          (if (words not= nil)
            ([:p (line-generator first-line)]
             [:p (line-generator second-line)]
             [:p (line-generator third-line)])
            ([:p "get inspired"])
          )
          [:submit-button :on-click get-words :text "inspire me more"]
        ]]]))

