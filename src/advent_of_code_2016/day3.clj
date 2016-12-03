(ns advent-of-code-2016.day3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  (->> (-> (slurp (io/resource "day3-input.txt"))
           (str/split #"\n"))
       (map #(str/trim %))
       (map (fn [line] (->> (str/split line #" ")
                            (filter (comp not empty?))
                            (map #(read-string %)))))))

(defn is-valid-triangle? [[a b c]]
  (and (> (+ a b) c) (> (+ b c) a) (> (+ a c) b)))

(defn part-1 []
  (->> input (filter is-valid-triangle?) (count)))

(defn part-2 []
  (->> (mapcat
         (fn [idx]
           (partition 3 (map #(nth % idx) input)))
         '(0 1 2))
       (filter is-valid-triangle?)
       (count)))
