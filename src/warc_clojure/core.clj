(ns warc-clojure.core
    (:require [clojure.java.io :as io]))

(import '(org.jwat.warc WarcRecord WarcReader WarcReaderFactory))


(defn get-warc-reader
  "Given a location of a .warc.gz file on disk, this
  routine produces a stream that can be read."
  [warc-gz-filename]
  (WarcReaderFactory/getReader (io/input-stream warc-gz-filename)))

(defn cast-record-as-map
  [record]
    {:content-length (.contentLength (.header record))
     :content-type (.contentType (.header record))
     :date (.warcDate (.header record))
     :filename (.warcFilename (.header record))
     :target-uri (.warcTargetUriUri (.header record))
     :target-uri-str (.warcTargetUriStr (.header record))
     :warc-type (.warcTypeStr (.header record))
     :payload-stream (.getPayloadContent record)})

(defn get-records-seq
  "The warc records in the file are returned as a sequence.
  The map above is returned"
  [warc-reader]
  (take-while identity (repeatedly #(cast-record-as-map (.getNextRecord warc-reader)))))
  
(defn get-records-uncast-seq
  "Get the original warc-record object itself in case you want to use it"
  [warc-reader]
  (take-while identity (repeatedly #(.getNextRecord warc-reader))))
  
(defn get-response-type
  "Return the response type: one of request, response or error"
  [record]
  (.contentTypeStr (.header record)))

(defn is-response-type?
  [record]
  (= "application/http; msgtype=response" (get-response-type record)))

(defn get-response-records-seq
  "Given a warc-reader, this returns a set of records that are of the type 'response'"
  [warc-reader]
  (filter 
    (fn [record-as-map]
      (= (:warc-type record-as-map) "response"))
    (get-records-seq warc-reader)))
