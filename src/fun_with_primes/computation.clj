(ns fun-with-primes.computation
  (:require [clojure.math.combinatorics :refer [cartesian-product]]))

;; I've implemented a Sieve of Eratosthenes (circa 200 B.C.), since a bit of research suggests its efficiency-to-complexity ratio is actually pretty good. The Sieve of Atkin (2003 A.D.) reportedly has better asymptotic complexity, but requires computing the number of solutions to two quadratic forms. If I needed better performance I would take a whack at it, and start by looking for quadratic solvers in Java.

(defn is-multiple?
  "Given a modulus and an integer, returns `true` if the number is a multiple of the modulus."
  [modulus number]
  (when (or (not (integer? modulus))
            (= modulus 0))
    (throw (Exception. "Modulus must be an integer greater than zero.")))
  (integer? (/ number modulus)))

(defn sieve
  "Given a modulus and a sequence of integers, yield only integers that are not a multiple of that modulus."
  [modulus numbers]
  (remove (partial is-multiple? modulus) numbers))

(defn primes-less-than
  "Given n, return all prime numbers less than or equal to n. This is not the problem we want to solve, but it's useful for testing our approach against the Prime Number Theorem."
  [n]
  (loop [primes []
         candidates (range 2 n)]
    (if (empty? candidates)
      primes
      (let [next-prime (first candidates)
            next-candidates (sieve next-prime candidates)]
        (recur (conj primes next-prime)
               next-candidates)))))

(defn first-primes
  "Given n, finds a set of primes of size n."
  [n]
  (loop [primes []
         candidates (rest (rest (range)))] ;; Two to infinity
    (if (= n (count primes))
      primes
      (let [next-prime (first candidates)
            next-candidates (sieve next-prime candidates)]
        (recur (conj primes next-prime)
               next-candidates)))))

(defn prime-products-with-1
  "The rows of the multiplication table for the first n prime numbers, with a 1 in the corner."
  [n]
  (let [numbers (into [1] (first-primes n))
        table-width (count numbers)
        pairs (cartesian-product numbers numbers)
        products (map #(apply * %) pairs)]
    (partition table-width products)))
