(ns advent-of-code-2016.day2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input 
  (-> (slurp (io/resource "day2-input.txt"))
      (str/split #"\n")))

;; part 1
(defn move [[x y] dir]
  (cond 
    (= dir \U) [x (max (dec y) -1)]
    (= dir \D) [x (min (inc y) 1)]
    (= dir \L) [(max (dec x) -1) y]
    (= dir \R) [(min (inc x) 1) y]))

(defn pos->num [d keypad]
  (fn [[x y]] (get-in keypad [(+ d y) (+ d x)])))

(defn compute-codes [move-fn input results curr]
  (if (empty? input) results
    (let [res (reduce move-fn curr (first input))]
      (recur move-fn (rest input) (conj results res) res))))

(defn part-1 []
  (let [keypad [[1 2 3] [4 5 6] [7 8 9]]
        p->n (pos->num 1 keypad)]
    (->> (compute-codes move input [] [0 0])
         (map p->n))))

;; part 2
(def keypad [[nil nil 1 nil nil]
             [nil 2 3 4 nil]
             [5 6 7 8 9]
             [nil \A \B \C nil]
             [nil nil \D nil nil]])

(defn diff-move [[x y] dir]
  (let [[u v] (cond 
                 (= dir \U) [x (max (dec y) -2)]
                 (= dir \D) [x (min (inc y) 2)]
                 (= dir \L) [(max (dec x) -2) y]
                 (= dir \R) [(min (inc x) 2) y])
        value (get-in keypad [(+ 2 v) (+ 2 u)])]
    (if (nil? value) [x y] [u v])))

(defn part-2 []
  (let [p->n (pos->num 2 keypad)]
    (->> (compute-codes diff-move input [] [-2 0])
         (map p->n))))
  
(println (part-1) (part-2))
