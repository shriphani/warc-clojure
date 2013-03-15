# warc-clojure

warc-clojure is a clojure wrapper around jwat-warc. It converts warc.gz files into
clojure sequences you can work with. warc.gz files are the standard format for archiving 
large datasets like large-scale web crawls. The LemurProject at CMU uses the warc.gz format
for shipping the ClueWeb datasets. The Internet Archive Project also uses the warc.gz format.

## Usage

<code>(warc-clojure.core/get-warc-reader "path/to/warc.gz")</code> gets you an InputStream.

<code>(warc-clojure.core/get-records-seq (warc-clojure.core "path/to/warc.gz"))</code> gives you access to a sequence
of maps which contain attributes of warc records.

If you want to work with the java WarcRecord object directly, 
you can use <code>(warc-clojure.core/get-records-uncast-seq (warc-clojure.core "path/to/warc.gz"))</code>.

The map returned for each record by <code>get-records-seq</code> have the following keys:


* <code> :content-length </code>
* <code> :content-type </code>
* <code> :date </code>
* <code> :filename </code>
* <code> :target-uri </code>
* <code> :target-uri-str </code>
* <code> :warc-type </code>
* <code> :payload-stream </code>


## License

Copyright © 2013 Shriphani Palakodety

Distributed under the MIT License.
