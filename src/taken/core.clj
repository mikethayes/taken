(ns taken.core
  (:require clojure.pprint))

(defn best-chunk-size
  [length sizes]
  {:pre [(some #(= 1 %) sizes)]}
  (first (filter #(>= length %) (sort >= sizes))))

(defn chunk-values
  "split the seq into best sized chunks based on chunk array values"
  [values chunk-sizes]
  (loop [chunks []
         v values]
    (if (empty? v)
     chunks
     (let [n (best-chunk-size (count v) chunk-sizes)]
       (recur (conj chunks (take n v)) (drop n v))))))
     ;(recur (conj chunks (take (best-chunk-size (count v) chunk-sizes) v)) (drop (best-chunk-size (count v) chunk-sizes) v)))))
