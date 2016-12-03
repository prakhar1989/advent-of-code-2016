(ns advent-of-code-2016.day3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  (map #(str/trim %)
       (-> (slurp (io/resource "day3-input.txt"))
           (str/split #"\n"))))

(def clean-input
  (->> input
       (map (fn [line]
              (->> (str/split line #" ")
                   (filter (comp not empty?))
                   (map #(read-string %)))))))

(defn part-1 []
  (->> clean-input
       (filter (fn [[a b c]]
                 (and (> (+ a b) c)
                      (> (+ b c) a)
                      (> (+ a c) b))))
       (count)))


