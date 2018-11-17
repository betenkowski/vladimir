(ns vladimir.core
  (:require [org.httpkit.server :refer [run-server]]))

(defn app [req]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body "{ \"text\" : \"Wombats rule! Koalas are cute!\" }"})

(defn -main [& args]
  (run-server app {:port 8080})
  (println "Vladimir started on port 8080"))
