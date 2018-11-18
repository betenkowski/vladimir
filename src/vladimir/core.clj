(ns vladimir.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.handler :refer [api]]
            [compojure.route :refer [not-found]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :as middleware]))

(def show-info
  {:body {:info "All wombats play Putin!"}})

(defn create-game []
  {:body {:id "wombat"}})

(defn get-game-log [id]
  {:body {:id id :log ["wombat" "koala" "hedgehod"]}})

(defroutes app-routes
           (GET "/" [] show-info)
           (POST "/games" [] (create-game))
           (GET "/games/:id/log" [id] (get-game-log id))
           (not-found "Not found"))

(def handler
  (-> (api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-params)
      (middleware/wrap-json-response)))

(defn -main [& _] (run-jetty handler {}))