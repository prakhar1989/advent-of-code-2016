(ns advent-of-code-2016.day3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  (->> (str/split (slurp (io/resource "day3-input.txt")) #"\n")
       (map #(str/trim %))
       (map (fn [line] (->> (str/split line #" ")
                            (filter (comp not empty?))
                            (map #(read-string %)))))))

(defn is-valid-triangle? [[a b c]]
  (and (> (+ a b) c) (> (+ b c) a) (> (+ a c) b)))

(defn part-1 [data]
  (->> data (filter is-valid-triangle?) (count)))

(defn part-2 []
  (->> (partition 3 (flatten (apply mapv vector input))) (part-1)))

(println (part-1 input) (part-2))
