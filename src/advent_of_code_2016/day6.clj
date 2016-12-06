(ns advent-of-code-2016.day6
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input 
  (-> (slurp (io/resource "day6-input.txt"))
      (str/split #"\n")))

(defn get-max-freq [compare-fn xs]
  (->> (frequencies xs) (into []) 
       (sort-by second compare-fn) (take 1) (first)))

(defn solve [compare-fn]
  (->> (range 0 8)
       (map (fn [i] (->> (map #(nth % i) input)
                         (get-max-freq compare-fn)
                         (first))))
       (apply str)))

; part 1, part 2
(println (solve >) (solve <))
