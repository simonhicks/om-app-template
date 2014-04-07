(ns {{name}}.core
  (:require [cemerick.austin.repls :refer (browser-connected-repl-js)]
            [compojure.route :refer (resources)]
            [compojure.core :refer (GET defroutes)]
            [clojure.string :as s]
            [ring.adapter.jetty :refer (run-jetty)]
            [hiccup.page :refer (html5 include-css)]))

(defn script [src]
  [:script {:src src, :type "text/javascript"}])

(defn new-page
  []
  (html5
    [:head
     (include-css "/css/styles.css")]
    [:body
     [:div#app]
     (script "http://fb.me/react-0.9.0.js")
     (script "out/goog/base.js")
     (script "{{sanitized}}.js")
     [:script {:type "text/javascript"}
      "goog.require('{{sanitized}}.core');"]]))

(defroutes site
  (resources "/")
  (GET "/" req (new-page)))

(defn run
  []
  (defonce ^:private server
    (run-jetty #'site {:port 8080 :join? false}))
  server)

; (run)

; (def repl-env (reset! cemerick.austin.repls/browser-repl-env (cemerick.austin/repl-env)))

;; Run the above commands, then (in a cljs buffer) run :Piggieback {{name}}/repl-env and refresh the browser
