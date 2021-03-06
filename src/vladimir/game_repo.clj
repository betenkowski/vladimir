(ns vladimir.game-repo
                                                                             (:require [vladimir.util :as util]))
(def games (atom {}))

(defn create []
  (let [id (util/rand-uuid)
        game {:id id :status :new :players (atom [])}]
    (swap! games #(assoc % id game))
    game))

(defmulti start-game :status)
(defmethod start-game :new [game] (assoc game :status :running))
;; TODO other states

(defn start [id] (swap! games #(update % id start-game)))