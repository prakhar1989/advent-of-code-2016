(ns advent-of-code-2016.day5
  (:import (java.security MessageDigest)
           (java.math BigInteger)))

(defn md5 [s]
  (apply str (map (partial format "%02x")
                  (.digest (doto (MessageDigest/getInstance "MD5") .reset
                             (.update (.getBytes s)))))))

(defn part-1 [s]
  (loop [res [] i 0]
    (if (= (count res) 2) (apply str res)
      (let [hash (md5 (str s i))
            zeros (take-while #(= % \0) hash)]
        (if (= (count zeros) 5)
          (recur (conj res (nth hash 5)) (inc i))
          (recur res (inc i)))))))

(defn print-pass [m]
 (->> (range 0 8)
      (map #(get m (char (+ 48 %)) (rand-int 10)))
      (apply str)))

(defn part-2 [s]
  (loop [res {} i 0]
    (print (print-pass res) "\r")
    (if (= (count res) 8) (print-pass res)
      (let [encrypted (md5 (str s i))]
        (if (and (= (take 5 (repeat \0)) (take 5 encrypted))
                 (nil? (get res (nth encrypted 5)))
                 (Character/isDigit (nth encrypted 5))
                 (< (Integer/parseInt (str (nth encrypted 5))) 8))
          (recur (assoc res (nth encrypted 5) (nth encrypted 6)) (inc i))
          (recur res (inc i)))))))

;; part 1
;(println (part-1 "ugkcyxxp"))
; part 2
;(part-2 "ugkcyxxp")
