(ns advent-of-code-2016.day7
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  (-> (slurp (io/resource "day7-input.txt"))
      (str/split #"\n")))

(defn is-abba? [x]
  (let [grps (->> (partition 2 1 x)
                  (filter (fn [[a b]] (= a b)))
                  (map #(apply str %)))
        same-nbrs? (fn [i] (= (get x (dec i)) (get x (+ i 2))))]
    (if (empty? grps) false
      (->> grps
          (map #(same-nbrs? (str/index-of x %)))
          (not-every? false?)))))

(defn supports-tls? [ip]
  (let [hypernets (->> ip (re-seq #"\[(\w+)\]") (map second) (set))
        parts (for [x (re-seq #"\w+" ip) :when (nil? (hypernets x))] x)]
    (and
      (not (empty? (filter is-abba? parts)))
      (empty? (filter is-abba? hypernets)))))

(defn part-1 []
  (->> input (filter supports-tls?) (count)))
