# warc-clojure

![CI Build](https://travis-ci.org/shriphani/warc-clojure.png?branch=master)

warc-clojure is a clojure wrapper around jwat-warc. It converts warc.gz files into
clojure sequences you can work with. warc.gz files are the standard format for archiving 
large datasets like large-scale web crawls. The LemurProject at CMU uses the warc.gz format
for shipping the ClueWeb datasets. The Internet Archive Project also uses the warc.gz format.

## Usage

Include in

* leiningen:

```clojure
[warc-clojure "0.2.6"]
```

* maven:

```xml
<dependency>
  <groupId>warc-clojure</groupId>
  <artifactId>warc-clojure</artifactId>
  <version>0.2.6</version>
</dependency>
```

* gradle:

```
compile "warc-clojure:warc-clojure:0.2.6"
```

Right now just reading is supported. The focus is on converting a warc
file into a stream of records that you can then process. Simple usage:

```clojure
warc-clojure.usage=> (clojure.pprint/pprint (get-response-records-seq (get-warc-reader "crlf_at_1k_boundary.warc.gz")))
({:content-length 4928,
  :content-type #<ContentType application/http; msgtype=response>,
  :date #inst "2011-02-18T23:32:56.000-00:00",
  :filename nil,
  :target-uri
  #<Uri http://whitiangamarine.tradeaboat.co.nz/emailAFriend.aspx?item=H4sIAGW4X00A%2fwFwAo%2f9gaXg6UTMkoLWV1Zy9nOhybsaOj36okTTM%2fCdGlV9et4wGW8ywbKoacCcFSjvDmf7BgE%2bke8eDGs5H4ib0RuE96Yj2%2fR5LIXmy1SUEue5IiHmYmS9jl9femiZGo6yAeW0fX%2bSnCkd5D%2bOW5216i0SJ9yb0PZJ%2fI%2f3z3manNAv042wJYFyUgOGpN6yV2wZGUEERk5FQI%2bmSASd88RTsytzksZuC%2fmTpDowhevXiY3N2%2br1n6Q9utfvEKuy5bonZPqy7BlK93yJ9DnviiT0ZJMsHGOTXC0NUywIonFpIXfogmm8y6I3RfXxQXD5p95qmiogdI1rvPgKCaV%2bgO4nZ4r%2fCAicl697pcwFKCQyFW5ZTS74%2bSnrdEssBdz2quceotYDcW2GH3hogkrRupiqN9hFdVsb2p3HXP%2fYGkH9W6%2bD8jp7TyLmALvnJJevST%2f6wlbQRhWrsNlPXnTjxQZrTw7z8E%2f%2bo5BFsb6HgWfXzULQZ2RnNFvAZOMgkcKtHopRTbA6cp5ifB8j8sFoV7PVwifNgcLBR28EKMjAeBqRZnBlB4nJwEISomyeNIBP%2fQlvpV4sqArZdUhs1qRi9TOQ%2fToiaSrlKpq%2bSdSbuZqjXIJ9b%2ftjgx8biQe129TDOB0BDHtEXwqq1aoaASxmTqddrYKqCRvcKjfH1aYSZHyL9p6xS6LwMAlO2myGxnZeGkrVpfr5C%2fEDJp6HR%2f28EgR4fdXyyRWauMhoPrQgXYJTq7NQwv7m8JYyvxCfGpX6Kz6ftu4NMBAHPuhGxd%2fEDDP5y3DUIcJBCAyMMvvMOJQXMXb8cpsyTv9ZcU1RN5ehrp2iyPudY%2b6iHHACAAA%3d>,
  :target-uri-str
  "http://whitiangamarine.tradeaboat.co.nz/emailAFriend.aspx?item=H4sIAGW4X00A%2fwFwAo%2f9gaXg6UTMkoLWV1Zy9nOhybsaOj36okTTM%2fCdGlV9et4wGW8ywbKoacCcFSjvDmf7BgE%2bke8eDGs5H4ib0RuE96Yj2%2fR5LIXmy1SUEue5IiHmYmS9jl9femiZGo6yAeW0fX%2bSnCkd5D%2bOW5216i0SJ9yb0PZJ%2fI%2f3z3manNAv042wJYFyUgOGpN6yV2wZGUEERk5FQI%2bmSASd88RTsytzksZuC%2fmTpDowhevXiY3N2%2br1n6Q9utfvEKuy5bonZPqy7BlK93yJ9DnviiT0ZJMsHGOTXC0NUywIonFpIXfogmm8y6I3RfXxQXD5p95qmiogdI1rvPgKCaV%2bgO4nZ4r%2fCAicl697pcwFKCQyFW5ZTS74%2bSnrdEssBdz2quceotYDcW2GH3hogkrRupiqN9hFdVsb2p3HXP%2fYGkH9W6%2bD8jp7TyLmALvnJJevST%2f6wlbQRhWrsNlPXnTjxQZrTw7z8E%2f%2bo5BFsb6HgWfXzULQZ2RnNFvAZOMgkcKtHopRTbA6cp5ifB8j8sFoV7PVwifNgcLBR28EKMjAeBqRZnBlB4nJwEISomyeNIBP%2fQlvpV4sqArZdUhs1qRi9TOQ%2fToiaSrlKpq%2bSdSbuZqjXIJ9b%2ftjgx8biQe129TDOB0BDHtEXwqq1aoaASxmTqddrYKqCRvcKjfH1aYSZHyL9p6xS6LwMAlO2myGxnZeGkrVpfr5C%2fEDJp6HR%2f28EgR4fdXyyRWauMhoPrQgXYJTq7NQwv7m8JYyvxCfGpX6Kz6ftu4NMBAHPuhGxd%2fEDDP5y3DUIcJBCAyMMvvMOJQXMXb8cpsyTv9ZcU1RN5ehrp2iyPudY%2b6iHHACAAA%3d",
  :warc-type "response",
  :payload-stream #< org.jwat.common.Payload$1@526489f0>})
```

Each record in the warc file is converted to a map with the following keys:

* <code> :content-length </code>
* <code> :content-type </code>
* <code> :date </code>
* <code> :filename </code>
* <code> :target-uri </code>
* <code> :target-uri-str </code>
* <code> :warc-type </code>
* <code> :payload-stream </code>

If you want a record object (without fiddling with the bits and
pieces), append the suffix <code>uncast</code> to the calls and you
get the Record objects themselves.


## License

Copyright © 2013 Shriphani Palakodety

Distributed under the MIT License.
