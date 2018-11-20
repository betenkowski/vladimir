(ns vladimir.util
  (:import (java.util UUID)))

;; TODO take a look at algo.generic

(defn map-values [f m] (into {} (map (fn [[key val]] [key (f val)]) m)))

(defn rand-uuid [] (.toString (UUID/randomUUID)))