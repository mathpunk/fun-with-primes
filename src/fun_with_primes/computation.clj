(ns fun-with-primes.computation
  (:require [clojure.math.combinatorics :refer [cartesian-product]]))

;; I've implemented a Sieve of Eratosthenes (circa 200 B.C.), since a bit of research suggests its efficiency-to-complexity ratio is actually pretty good. The Sieve of Atkin (2003 A.D.) reportedly has better asymptotic complexity, but requires computing the number of solutions to two quadratic forms. If I needed better performance I would start by looking for quadratic solvers in Java.

(defn is-multiple? [modulus number]
  (when (or (not (integer? modulus))
            (= modulus 0))
    (throw (Exception. "Modulus must be an integer greater than zero.")))
  (integer? (/ number modulus)))

(defn sieve
  [modulus numbers]
  (remove (partial is-multiple? modulus) numbers))

(defn first-primes [n]
  (loop [primes []
         candidates (rest (rest (range)))] ;; Two to infinity
    (if (= n (count primes))
      primes
      (let [next-prime (first candidates)
            next-candidates (sieve next-prime candidates)]
        (recur (conj primes next-prime)
               next-candidates)))))

;; The data for our multiplication table.
(defn prime-products-with-1
  "The rows of the table, with a 1 in the corner."
  [n]
  (let [numbers (into [1] (first-primes n))
        table-width (count numbers)
        pairs (cartesian-product numbers numbers)
        products (map #(apply * %) pairs)]
    (partition table-width products)))
