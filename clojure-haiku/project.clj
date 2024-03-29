(defproject clojure-haiku "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"], 
                 [clj-http "3.10.0"], 
                 [ring/ring-json "0.4.0"], 
                 [ring/ring-core "1.7.1"],
                 [ring/ring-jetty-adapter "1.7.1"],
                 [ring/ring-devel "1.7.1"],
                 [org.clojure/data.json "0.2.6"],
                 [hiccup "1.0.5"],
                 [compojure "1.5.1"],
                 [cheshire "5.8.1"]]
  :main ^:skip-aot clojure-haiku.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :ring {:handler clojure-haiku.core/handler}
  :plugins [[lein-ring "0.12.1"]])
