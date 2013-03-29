(ns warc-clojure.usage
  (:use [warc-clojure.core :as core])
  (:use [clojure.tools.cli :only (cli)])
  (:gen-class :main true))

(defn print-items
  [record items-bool-map]
  (when (:content-length items-bool-map) 
    (print "Content Length:\t" (:content-length record) "\n"))
  (when (:date items-bool-map)
    (print "Date:\t" (:date record) "\n"))
  (when (:content-type items-bool-map)
    (print "Content Type:\t" (:content-type record) "\n"))
  (when (:target-uri items-bool-map)
    (print "Target Uri:\t" (:target-uri record) "\n"))
  (when (:warc-type items-bool-map)
    (print "Warc Type:\t" (:warc-type record)) "\n")
  (when (:payload items-bool-map)
    (print "Payload:\n" (slurp (:payload-stream record))))
  (when (:print-separator items-bool-map)
  	(println "############################################")))


(defn -main
  [& args]
  (let [[args-vector [warc-gz-filename] banner] 
  			(cli args ["--content-length" "Prints out the sizes of each file" :flag true]
  					  ["--content-type" "Prints out the content type" :flag true]
  					  ["--date" "Prints out the date of download" :flag true]
  					  ["--target-uri" "Prints out the url of the warc file" :flag true]
  					  ["--warc-type" "Prints out the warc-type field" :flag true]
  					  ["--payload" "Prints out the html stream"]
  					  ["--print-separator" "Prints a separator between records" :flag true])]

  		(doseq [record (core/get-http-records-seq 
							(core/get-warc-reader warc-gz-filename))]
      		(print-items record args-vector))))