(ns warc-clojure.core
  (:require [clojure.java.io :as io])
  (:import [org.jwat.warc WarcRecord WarcReader WarcReaderFactory]))

(defn get-warc-reader
  "Given a location of a .warc.gz file on disk, this
  routine produces a stream that can be read."
  [warc-gz-filename]
  (WarcReaderFactory/getReader (io/input-stream warc-gz-filename)))

(defn cast-record-as-map
  "
    Takes a default jwat-warc object
    and builds a map out of it. The map fields are:
      - content-length
      - content-type
      - filename
      - target-uri
      - target-uri-str
      - warc-type
      - payload-stream
    Each of the fields are self-explanatory
  "
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
  (map (fn [record] (cast-record-as-map record)) (take-while identity (repeatedly #(.getNextRecord warc-reader)))))
  
(defn get-records-uncast-seq
  "Get the original warc-record object itself in case you want to use it"
  [warc-reader]
  (take-while identity (repeatedly #(.getNextRecord warc-reader))))
  
(defn get-response-type
  "Return the response type: one of request, response or error"
  [record]
  (.contentTypeStr (.header record)))

(defn get-response-records-seq
  "Given a warc-reader, this returns a set of records that are of the type 'response'"
  [warc-reader]
  (filter 
   (fn [record-as-map]
     (= (:warc-type record-as-map) "response"))
   (get-records-seq warc-reader)))

(defn get-http-records-seq
  "Produces a list of http records in the warc-gz file"
  [warc-reader]
  (filter
   (fn [record-as-map]
     (= "http" (.getScheme (java.net.URI. (:target-uri-str record-as-map)))))
   (get-response-records-seq warc-reader)))

