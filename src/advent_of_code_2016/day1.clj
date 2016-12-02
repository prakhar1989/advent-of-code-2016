(ns advent-of-code-2016.day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def compass {:north 0 :east 1 :south 2 :west 3})
(def turn {\R 1 \L -1})

(defn new-pos [[dir [x y]] step]
  (let [delta (->> (rest step) (apply str) (read-string))
        newdir (mod (+ (compass dir) (turn (first step))) 4)]
    (cond
      (= newdir 0) [:north [x (+ delta y)]]
      (= newdir 1) [:east  [(+ x delta) y]]
      (= newdir 2) [:south [x (- y delta)]]
      (= newdir 3) [:west  [(- x delta) y]])))

(defn get-steps [filename]
  (let [input (slurp (io/resource filename))]
    (->> (str/split input #",") (map str/trim))))

(defn part-1 [steps]
  (let [[_ [x y]] (reduce new-pos [:north [0 0]] steps)]
    (+ (Math/abs x)
       (Math/abs y))))

(defn get-visited-points [[a b] [c d] dir]
  (if (or (= dir :north) (= dir :south))
    (if (> a c)
      (map (fn [x] [x b]) (range a c -1))
      (map (fn [x] [x b]) (range a c)))
    (if (> b d)
      (map (fn [x] [a x]) (range b d -1))
      (map (fn [x] [a x]) (range b d)))))

(defn new-visited [points visited]
  (if (empty? points) visited
    (let [point (first points)]
      (if (contains? visited point) point
        (recur (rest points) (conj visited point))))))

(defn part-2 [input]
  (loop [steps input
         pos [0 0]
         dir :north
         visited #{}]
    (if (empty? steps) false ; not found
      (let [[ndir npos] (new-pos [dir pos] (first steps))
            points (get-visited-points pos npos dir)
            nvisited (new-visited points visited)]
        (if (not (set? nvisited))
          (reduce + (map #(Math/abs %) nvisited)) ;found the ans
          (recur (rest steps) npos ndir nvisited))))))

(def steps (get-steps "day1-input.txt"))

(println (part-1 steps))
(println (part-2 steps))

