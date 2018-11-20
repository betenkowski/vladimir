(ns vladimir.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.handler :refer [api]]
            [compojure.route :refer [not-found]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :as middleware]
            [vladimir.game-repo :as repo]
            [vladimir.util :as util]))

(def show-info
  {:body {:info "All wombats play Putin!"}})

(defmulti render-game :status)
(defmethod render-game :new [game] (update game :players deref))
(defmethod render-game :running [game] "wombat")

(defn get-games []
  {:body (util/map-values render-game @repo/games)})

(defn get-game [id]
  {:body (render-game (@repo/games id))})

(defn create-game []
  {:body (render-game (repo/create))})

(defn get-game-log [id]
  {:body {:id id :log ["wombat" "koala" "hedgehog"]}})

(defroutes app-routes
           (GET "/" [] show-info)
           (GET "/games" [] (get-games))
           (GET "/games/:id" [id] (get-game id))
           (POST "/games" [] (create-game))
           (POST "/games/:id/start" [id] (repo/start id))
           (GET "/games/:id/log" [id] (get-game-log id))
           (not-found "Not found"))

(def handler
  (-> (api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-params)
      (middleware/wrap-json-response)))

(defn -main [& _] (run-jetty handler {}))