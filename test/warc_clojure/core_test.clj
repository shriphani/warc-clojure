(ns warc-clojure.core-test
  (:use clojure.test
        warc-clojure.core))

(def test-file "crlf_at_1k_boundary.warc.gz")

(deftest warc-reader-test
  (testing "Read a warc file and fetch records"
    (is
     (-> test-file
         get-warc-reader
         get-response-records-seq
         count
         (= 1)))))
