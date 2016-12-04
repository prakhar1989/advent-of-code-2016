(ns advent-of-code-2016.day4
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  (str/split (slurp (io/resource "day4-input.txt")) #"\n"))

(defn parse [line]
  (let [id (->> (re-find #"\d+" line) (read-string))
        checksum (last (re-find #"\[(\w+)\]" line))
        code (->> (str/split line #"-") (reverse) (rest) (str/join "-"))]
    {:id id :checksum checksum :code code}))

(def lines (map parse input))

(defn is-valid-room? [room]
  (let [counts (frequencies (:code room))
        checksum (->> (into [] counts)
                      (sort-by (fn [[k v]] [(- v) k]))
                      (map first)
                      (take 5)
                      (apply str))]
    (= checksum (:checksum room))))

(defn part-1 []
  (->> lines (filter is-valid-room?) (map :id) (reduce +)))

(defn decrypt [ch shift]
  (if (= ch \-) \space
    (char (+ (mod (+ (- (int ch) (int \a))
                     shift)
                  26)
             (int \a)))))

(defn part-2 []
  (let [decryped-lines (->> lines
                            (mapv (fn [line]
                                   (->> (:code line)
                                        (map #(decrypt % (:id line)))
                                        (apply str)))))
        query (->> decryped-lines
                   (filter #(str/includes? % "north"))
                   (first))
        idx (.indexOf decryped-lines query)]
    (:id (nth lines idx))))

