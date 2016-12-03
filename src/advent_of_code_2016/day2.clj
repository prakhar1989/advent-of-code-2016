(ns advent-of-code-2016.day2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def keypad [[1 2 3] [4 5 6] [7 8 9]])

(defn pos->num [[x y]]
  (get-in keypad [(inc y) (inc x)]))

(defn move [[x y] dir]
  (cond 
    (= dir \U) [x (max (dec y) -1)]
    (= dir \D) [x (min (inc y) 1)]
    (= dir \L) [(max (dec x) -1) y]
    (= dir \R) [(min (inc x) 1) y]))

(defn compute-codes [input results curr]
  (if (empty? input) results
    (let [res (reduce move curr (first input))]
      (recur (rest input) (conj results res) res))))

(def input 
  (-> (slurp (io/resource "day2-input.txt"))
      (str/split #"\n")))

(def part-1 
  (->> (compute-codes input [] [0 0])
       (map pos->num)))

