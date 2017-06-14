(ns fun-with-primes.core
  (:gen-class)
  (:require [clojure.math.combinatorics :refer [cartesian-product]]))

;; I've implemented a Sieve of Eratosthenes, since a bit of research seemed to suggest that its efficiency-to-complexity ratio is not terrible. The Sieve of Atkin reportedly has better asymptotic complexity, but requires a bit of quadratic solving that it's not immediately obvious how to do efficiently.

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
         candidates (range 2 n)]
    (if (empty? candidates)
      primes
      (let [next-prime (first candidates)
            next-candidates (sieve next-prime candidates)]
        (recur (conj primes next-prime)
               next-candidates)))))

;; This is actually my first prime finder for this problem, but it didn't seem like the transients were giving much of a boost. I must not understand when they are worthwhile.
(defn first-primes-with-transients [n]
  (loop [primes (transient [])
         candidates (range 2 n)]
    (if (empty? candidates)
      (persistent! primes)
      (let [next-prime (first candidates)
            next-candidates (sieve next-prime candidates)]
        (recur (conj! primes next-prime)
               next-candidates)))))

;; The data for our multiplication table.
(defn prime-products
  "The rows of the table, with a 1 in the corner."
  [n]
  (let [numbers (into [1] (first-primes n))
        table-width (count numbers)
        pairs (cartesian-product numbers numbers)
        products (map #(apply * %) pairs)]
    (partition table-width products)))

;; The string representation for our table.

(defn pad
  "Given a length and an integer, return the string with spaces prepending that integer's string representation."
  [padding n]
  (let [number (str n)]
    (str (apply str (repeat (- padding (count number)) " ")) number)))


(->> (flatten (prime-products 10))
     (map str)
     (map count)
     (reduce max))

(defn print-table [n]
  (let [data (prime-products n)
        padding (->> (flatten data)
                     (map str)
                     (map count)
                     (reduce max))
        pretty-data (map (fn [row]
                           (map (fn [entry] (pad padding entry))
                                row) data))]
    (map println pretty-data)))

(print-table 10)



(defn -main
  "I don't do a whole lot ... yet."
  [arg]
  (println (first-primes (Integer. arg))))
