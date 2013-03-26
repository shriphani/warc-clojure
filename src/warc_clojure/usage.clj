(ns warc-clojure.usage
  (:use [warc-clojure.core :as core])
  (:use [clojure.tools.cli :only (cli)])
  (:gen-class :main true))

(defn -main
  [& args]
  (first (core/get-response-records-seq (core/get-warc-reader (first (second (cli args)))))))
