(ns advent-of-code-2016.day2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input 
  (-> (slurp (io/resource "day2-input.txt"))
      (str/split #"\n")))

(defn pos->num [d keypad]
  (fn [[x y]] (get-in keypad [(+ d y) (+ d x)])))

(defn get-movefn [d]
  (fn [[x y] dir]
    (let [[u v] (cond (= dir \U) [x (max (dec y) (- d))]
                      (= dir \D) [x (min (inc y) d)]
                      (= dir \L) [(max (dec x) (- d)) y]
                      (= dir \R) [(min (inc x) d) y])
          value (get-in keypad [(+ d v) (+ d u)])]
      (if (nil? value) [x y] [u v]))))

(defn compute-codes [move-fn input results curr]
  (if (empty? input) results
    (let [res (reduce move-fn curr (first input))]
      (recur move-fn (rest input) (conj results res) res))))

(defn part-1 []
  (let [keypad [[1 2 3] [4 5 6] [7 8 9]]
        p->n (pos->num 1 keypad)
        move-fn (get-movefn 1)]
    (->> (compute-codes move-fn input [] [0 0])
         (map p->n))))

(defn part-2 []
  (let [keypad [[nil nil 1 nil nil] [nil 2 3 4 nil]
                [5 6 7 8 9] [nil \A \B \C nil]
                [nil nil \D nil nil]]
        p->n (pos->num 2 keypad)
        move-fn (get-movefn 2)]
    (->> (compute-codes move-fn input [] [-2 0])
         (map p->n))))
  
(println (part-1) (part-2))
