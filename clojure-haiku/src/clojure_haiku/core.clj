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
            [clojure-haiku.views :as view]
            [clojure-haiku.layout :as layout]))


(defroutes app-routes
  (GET "/" [] (layout/application "Home" (view/main)))
  (GET "/random-haiku" [word-list] (layout/application "shitty haikus" (view/random-haiku m/get-word-list)))
  (route/resources "/")
  ; if page not found
  (ANY "*" [] (route/not-found (layout/application "page not found" (view/not-found)))))
  

(def handler
  (handler/site app-routes))
