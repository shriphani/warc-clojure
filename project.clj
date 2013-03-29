(defproject org.clojars.shriphani/warc-clojure "0.2.3-SNAPSHOT"
  :description "Wrapper around the jwat-warc Java Library"
  :url "https://github.com/shriphani/warc-clojure"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.jwat/jwat-warc "1.0.0"]
                 [org.clojure/tools.cli "0.2.2"]
                 [clojurewerkz/urly "1.0.0"]]
  :main warc-clojure.usage)  
