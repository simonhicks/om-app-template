(ns {{name}}.core
  (:require [cemerick.austin.repls :refer (browser-connected-repl-js)]
            [net.cgrand.enlive-html :as enlive]
            [compojure.route :refer (resources)]
            [compojure.core :refer (GET defroutes)]
            [clojure.string :as s]
            [ring.adapter.jetty :refer (run-jetty)]
            [clojure.java.io :as io]))

(enlive/deftemplate page
  (io/resource "index.html")
  []
  [:body] (enlive/append
            (enlive/html [:script (browser-connected-repl-js)])))

(defroutes site
  (resources "/")
  (GET "/" req (page)))

(defn run
  []
  (defonce ^:private server
    (run-jetty #'site {:port 8080 :join? false}))
  server)

; (run)

; (def repl-env (reset! cemerick.austin.repls/browser-repl-env (cemerick.austin/repl-env)))

;; Run the above commands, then (in a cljs buffer) run :Piggieback {{name}}/repl-env and refresh the browser
