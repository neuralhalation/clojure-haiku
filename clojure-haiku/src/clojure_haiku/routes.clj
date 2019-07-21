(ns clojure-haiku.routes
  (:require
    [compojure.core :refer [defroutes GET POST]]
    [compojure.route :refer [not-found]]
    [clojure-haiku.views :as v]))

(defroutes routes
  (GET "/" [] (v/main)))
