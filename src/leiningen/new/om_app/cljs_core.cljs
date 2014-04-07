(ns {{name}}.core
  (:require [om.core :as om :include-macros true] 
            [om.dom :as dom :include-macros true]
            [clojure.data :as data]
            [clojure.string :as s]
            [clojure.browser.repl]))

;; This is so react/om can handle native js strings

(extend-type string
  ICloneable
  (-clone [s] (js/String. s)))

(extend-type js/String
  ICloneable
  (-clone [s] (js/String. s))
  om/IValue
  (-value [s] (str s)))

;; Utility widget to make text editable

(defn display? [show]
  (if show
    #js {}
    #js {:display "none"}))

(defn handle-change [e owner]
  (om/set-state! owner :text (.. e -target -value)))

(defn commit-change [thing owner]
  (om/set-state! owner :editing false)
  (om/transact! thing (fn [_]
                        (let [parser (om/get-state owner :parser)]
                          (parser (om/get-state owner :text))))))

(defn editable [thing owner]
  (reify
    om/IInitState
    (init-state [_]
      {:editing false :formatter om/value :parser identity})
    om/IWillMount
    (will-mount [_]
      (om/set-state! owner :text ((om/get-state owner :formatter) thing)))
    om/IRenderState
    (render-state [_ {:keys [editing text]}]
      (dom/span nil
              (dom/span #js {:style (display? (not editing))
                             :onClick #(om/set-state! owner :editing true)} text)
              (dom/input #js {:style (display? editing)
                              :value text
                              :onChange #(handle-change % owner)
                              :onKeyPress #(when (== (.-keyCode %) 13)
                                             (commit-change thing owner))
                              :onBlur  (fn [e] (commit-change thing owner))})))))

;; the app itself

(def app-state
  (atom {:text "Hello Om!"}))

(defn app-view
  [app owner]
  (reify
    om/IRender
    (render [_]
      (let [text (:text app)]
        (dom/div nil
               (dom/h2 nil (om/build editable text))
               (dom/button #js {:onClick #(js/alert @text)} "Alert"))))))

(om/root app-view app-state
         {:target (. js/document (getElementById "app"))})
