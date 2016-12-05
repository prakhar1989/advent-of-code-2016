(ns advent-of-code-2016.day5
  (:import (java.security MessageDigest)
           (java.math BigInteger)))

(defn md5 [s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        size (* 2 (.getDigestLength algorithm))
        raw (.digest algorithm (.getBytes s))
        sig (.toString (BigInteger. 1 raw) 16)
        padding (apply str (repeat (- size (count sig)) "0"))]
    (str padding sig)))

(defn part-1 [s]
  (loop [res [] i 0]
    (if (= (count res) 8) (apply str res)
      (let [hash (md5 (str s i))
            zeros (take-while #(= % \0) hash)]
        (if (= (count zeros) 5)
          (recur (conj res (nth hash 5)) (inc i))
          (recur res (inc i)))))))

(defn part-2 [s]
  (loop [res [] i 0 found #{}]
    (if (= (count res) 8) res
      (let [hash (md5 (str s i))
            zeros (take-while #(= % \0) hash)]
        (if (and (= (count zeros) 5)
                 (not (contains? found (nth hash 5)))
                 (Character/isDigit (nth hash 5))
                 (< (Integer/parseInt (str (nth hash 5))) 8))
          (recur (conj res {:val (nth hash 6)
                            :pos (nth hash 5) })
                 (inc i)
                 (conj found (nth hash 5))
          (recur res (inc i) found))))))

;; part 1
; (println (part-1 "ugkcyxxp"))
