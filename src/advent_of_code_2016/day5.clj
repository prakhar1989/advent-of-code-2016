(ns advent-of-code-2016.day5
  (:import (java.security MessageDigest)
           (java.math BigInteger)))

; returns the MD5 hash of a string s
; courtesy - https://gist.github.com/jizhang/4325757
(defn md5 [s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        size (* 2 (.getDigestLength algorithm))
        raw (.digest algorithm (.getBytes s))
        sig (.toString (BigInteger. 1 raw) 16)
        padding (apply str (repeat (- size (count sig)) "0"))]
    (str padding sig)))

(def input "abc")

(defn do-mining [s]
  (loop [res [] i 0]
    (if (= (count res) 8) res
      (let [hash (md5 (str s i))
            zeros (take-while #(= % \0) hash)]
        (if (= (count zeros) 5)
          (recur (conj res (nth hash 5)) (inc i))
          (recur res (inc i)))))))
