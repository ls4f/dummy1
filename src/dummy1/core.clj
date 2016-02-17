(ns dummy1.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.java.io :refer [input-stream output-stream]]
            [clojure.string :refer [blank?]])
  (:import (org.firebirdsql.management FBStreamingBackupManager
                                       FBBackupManager)
           (java.util.zip GZIPOutputStream
                          GZIPInputStream))
  (:gen-class))

(def cli-options
  [["-p" "--port PORT" "Firebird port"
    :default 3050
    :id :port
    :parse-fn #(Integer/parseInt %)]
   ["-u" "--user USER" "Firebird user"
    :id :user
    :default "SYSDBA"]
   ["-k" "--key PASSWORD" "Firebird password"
    :id :pass
    :default "masterkey"]
   ["-f" "--backupfile file-path" "The .fbk file to use for backup/restore"
    :id :fbk
    :default "test.fbk"]
   ["-d" "--database db-path"
    :id :fdb
    :default "test.fdb"]
   ["-h" "--host HOST" "Hostname/IP of the Firebird host"
    :id :host
    :default "127.0.0.1"]
   ["-l" "--log logfile-path" "File to use for the verbose output of a restore operation" :id :log]
   ["-v" "--verbose" "Verbose output of a restore operation (has no effect on backup). Will use stdout by default" :id :verb]
   ["-b" "--backup" "We would like to backup a databasse" :id :backup]
   ["-r" "--restore" "A restore function would take place" :id :restore]
   ["-o" "--replace" "A restore with enabled overwrite" :id :rep]
   ["-g" "--gzip" "Whether to use the built-in GZIP I/O streams in the JVM" :id :gz]])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{:keys [options summary errors]} (parse-opts args cli-options)]
    (when (or errors
              (not (or (:backup options)
                       (:restore options)
                       (:rep options))))
      (do
        (when errors
          (println errors))
        (println summary)
        (System/exit 1)))
    (let [{:keys [port user pass fdb host fbk gz log verb rep]} options
          manager (doto (FBStreamingBackupManager.)
                    (.setHost host)
                    (.setPort port)
                    (.setUser user)
                    (.setPassword pass)
                    (.setLogger System/out)
                    (.setVerbose (boolean verb))
                    (.setDatabase fdb)
                    (.setRestoreReplace (boolean rep)))
          fbk-stream (if (:backup options)
                       (let [raw-io (output-stream fbk)]
                         (if gz
                           (GZIPOutputStream. raw-io)
                           raw-io))
                       (let [raw-io (input-stream fbk)]
                         (if gz
                           (GZIPInputStream. raw-io)
                           raw-io)))
          the-op (if (:backup options)
                   #(with-open [o fbk-stream]
                      (doto manager
                        (.setBackupOutputStream o)
                        (.backupDatabase FBBackupManager/BACKUP_NO_GARBAGE_COLLECT)))
                   #(with-open [i fbk-stream]
                      (doto manager
                        (.setRestoreInputStream i)
                        (.restoreDatabase))))]
      (time (if (blank? log)
              (the-op)
              (with-open [l (output-stream log)]
                (.setLogger manager l)
                (the-op))))
      0)))
