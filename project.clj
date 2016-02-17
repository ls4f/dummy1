(defproject dummy1 "0.1.0-SNAPSHOT"
  :description "A really dummy stub to test streaming backup/restore with Jaybird"
  :url "http://github.com/ls4f/dummy1"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.3.7"]
                 [org.clojure/tools.cli "0.3.3"]]
  :main ^:skip-aot dummy1.core
  :target-path "target/%s"
  :profiles {:provided {:resource-paths ["/home/ls4f/java/jaybird/output/lib/jaybird-full-3.0.0-SNAPSHOT.jar"]}
             :uberjar {:aot [dummy1.core]}})
