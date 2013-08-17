(ns woohoo.core
  (:require [ring.server.standalone :refer [serve]]
            [compojure.handler :refer [site]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.resource :refer [wrap-resource]]
            [compojure.core :refer [defroutes GET POST]]
            [hiccup.core :refer [html]]
            [ring.util.response :refer [redirect]]
            [clojure.string :as string]))

(defn layout
  []
  (html [:html
         [:head]
         [:body
          [:script {:src "/js/debug.js"}]]]))

(defroutes routes
  (GET "/" {params :params} (layout)))

(def handler
  (-> #'routes
      site
      (wrap-resource "public")
      (wrap-file-info)))

(defonce server-process (atom nil))

(defn stop-server!
  []
  (when-let [s @server-process]
    (.stop s)
    (reset! server-process nil)))

(defn start-server!
  []
  (stop-server!)
  (reset! server-process (serve handler {:port 3000 :open-browser? false :join? false})))

(defn -main
  [& args]
  (start-server!))
