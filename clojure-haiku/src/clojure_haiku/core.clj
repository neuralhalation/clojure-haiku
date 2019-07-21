(ns clojure-haiku.core
  (:gen-class)
  (:use ring.middleware.params
        ring.util.response
        ring.adapter.jetty)
  (:require [clojure-haiku.middleware :as m]
            [clojure-haiku.routes :as r]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure-haiku.views :as view]))


(defroutes app-routes
  (GET "/" [words] (view/main m/get-words))
  (route/resources "/")
  ; if page not found
  (route/not-found "Page not found"))
  
(def handler
  (handler/site app-routes))
