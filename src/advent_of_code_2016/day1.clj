(ns advent-of-code-2016.day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn new-pos [[dir [x y]] step]
  (let [d (first step)
        delta (->> (rest step) (apply str) (read-string))]
    (cond
      (= dir :north)
        (if (= d \R)
          [:east [(+ x delta) y]]
          [:west [(- x delta) y]])
      (= dir :south)
        (if (= d \R)
          [:west [(- x delta) y]]
          [:east [(+ x delta) y]])
      (= dir :east)
        (if (= d \R)
          [:south [x (- y delta)]]
          [:north [x (+ y delta)]])
      (= dir :west)
        (if (= d \R)
          [:north [x (+ y delta)]]
          [:south [x (- y delta)]]))))

(defn get-dist [filename]
  (let [input (slurp (io/resource filename))
        steps (->> (str/split input #",") (map str/trim))
        [_ [x y]] (reduce new-pos [:north [0 0]] steps)]
    (+ (Math/abs x)
       (Math/abs y))))


(println (get-dist "day1-input.txt"))
