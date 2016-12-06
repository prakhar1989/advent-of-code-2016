(ns advent-of-code-2016.day6
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input 
  (-> (slurp (io/resource "day6-input.txt"))
      (str/split #"\n")))


(defn get-max-freq [xs]
  (->> (frequencies xs) (into []) 
       (sort-by second >) (take 1) (first)))

(defn part-1 []
  (->> (range 0 8)
       (map (fn [i] (->> (map #(nth % i) input)
                         (get-max-freq)
                         (first))))
       (apply str)))
  


