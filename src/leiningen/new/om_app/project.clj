(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2156"]
                 [om "0.5.0"]
                 [ring "1.2.1"]
                 [compojure "1.1.6"]
                 [enlive "1.1.5"]]

  :plugins [[lein-cljsbuild "1.0.2"]]

  :source-paths ["src/cljs" "src/clj"]

  :profiles {:dev {:plugins [[com.cemerick/austin "0.1.3"]
                             [lein-cljsbuild "1.0.2"]]

                   :cljsbuild {
                     :builds [{:id "{{name}}"
                               :source-paths ["src/cljs"]
                               :compiler {
                                 :output-to "resources/public/{{sanitized}}.js"
                                 :output-dir "resources/public/out"
                                 :optimizations :none
                                 :source-map true}}]}}})
