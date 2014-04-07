(ns leiningen.new.om-app
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "om-app"))

(defn om-app
  "Basic compojure/om app template for Leiningen"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' om-app project.")
    (->files data
             [".gitignore" (render "gitignore" data)]
             ["project.clj" (render "project.clj" data)]
             ["resources/public/css/styles.css" (render "styles.css" data)]
             ["src/clj/{{sanitized}}/core.clj" (render "clj_core.clj" data)]
             ["src/cljs/{{sanitized}}/core.cljs" (render "cljs_core.cljs" data)]
             )))

