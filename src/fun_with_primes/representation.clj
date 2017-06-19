(ns fun-with-primes.representation
  (:require [fun-with-primes.computation :as compute]))

(defn padding
  "Compute the maximum character length of the items in a list."
  [lists]
  (->> (flatten lists)
       (map str)
       (map count)
       (reduce max)))

(defn pad
  "Given a length and an integer, return the string with spaces prepending that integer's string representation."
  [padding n]
  (let [number (str n)]
    (str (apply str (repeat (- padding (count number)) " ")) number)))

(defn pretty-data-with-1
  "Given a seq of seqs of numbers, return a seq of seqs of string representations of those numbers, padded with spaces to be of the same length."
  [rows]
  (map (fn [row] (map (partial pad (padding rows)) row)) rows))

(defn pretty-data
  "One isn't prime. This makes a seq of seqs associative so that we can assoc-in the first item of the first row and make it blank."
  [table]
  (let [associative-table (vec (map vec table))
        one (first (first table))
        padding (count one)
        blank (apply str (repeat padding " "))]
    (assoc-in associative-table [0 0] blank)))

(defn prime-products-table
  [n]
  (->> (compute/prime-products-with-1 n)
       pretty-data-with-1
       pretty-data))
