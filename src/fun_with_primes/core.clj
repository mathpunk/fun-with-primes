(ns fun-with-primes.core
  (:gen-class)
  (:require [fun-with-primes.representation :refer [prime-products-table]]))

(defn -main
  "`lein run <integer>` prints a table of the products of all primes less than or equal to the integer."
  [arg]
  (let [n (Integer. arg)]
    (doall (map println (prime-products-table n)))))
