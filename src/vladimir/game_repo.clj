(ns vladimir.game-repo
                                                                             (:require [vladimir.util :as util]))
(def games (atom {}))

(defn create-game []
  (let [id (util/rand-uuid)
        game {:id id :status :new :players (atom [])}]
    (swap! games #(assoc % id game))
    game))