(ns advent-of-code-2016.day4
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  (str/split (slurp (io/resource "day4-input.txt")) #"\n"))

(defn parse [line]
  (let [id (->> (re-find #"\d+" line) (read-string))
        checksum (last (re-find #"\[(\w+)\]" line))
        code (->> (str/split line #"-") (reverse) (rest) (apply str))]
    {:id id :checksum checksum :code code}))

(defn is-valid-room? [room]
  (let [counts (frequencies (:code room))
        checksum (->> (into [] counts)
                      (sort-by (fn [[k v]] [(- v) k]))
                      (map first) (take 5) (apply str))]
    (= checksum (:checksum room))))

(defn part-1 []
  (->> (map parse input) (filter is-valid-room?)
       (map :id) (reduce +)))
