(ns woohoo
  (:require [dommy.utils :as utils]
            [dommy.core :as dommy])
  (:require-macros [dommy.macros :refer [sel sel1 deftemplate]]))

(defn t
  [ts]
  (->> (for [i (range (rand 10) (+ 10 (rand 10)))]
             [i (* i 10)  (* i 20)])
       (map (juxt second #(+ (first %) (last %))))
       set))

(deftemplate pre
  [c]
  [:p (pr-str c)])

(defn up
  [t]
  (dommy/set-text! (sel1 :p) t))

(defn app 
  []
  (-> (sel1 :body)
      (dommy/append! (pre "hello"))
      (dommy/listen! :click (fn [e] (up (t))))))
 
(app)
