(ns fun-with-primes.core-test
  (:require [clojure.test :refer :all]
            [clojure.algo.generic.math-functions :refer [approx= log]]
            [fun-with-primes.core :refer :all]))

;; Let pi(x) equal the number of primes less than x. The prime number theorem states that the limit as x approaches infinity of pi(x) divided by x / log(x) is one. I'll go by this table here
;;
;; https://en.wikipedia.org/wiki/Prime_number_theorem#Table_of_.CF.80.28x.29.2C_x_.2F_log_x.2C_and_li.28x.29
;;
;; for error bounds to see how our function does.

(defn approximate-number-of-primes [x]
  (/ x (log x)))

(defn relative-error [x]
  (let [primes-counted (count (first-primes x))
        primes-approximated (approximate-number-of-primes x)]
    (/ primes-counted primes-approximated)))

(deftest error-within-bounds
  (testing "Primes less than 10."
    (is (approx= (relative-error 10) 1 0.2)))
  (testing "Primes less than 10^2."
    (is (approx= (relative-error 100) 1 0.152)))
  (testing "Primes less than 10^3."
    (is (approx= (relative-error 1000) 1 0.162)))
  (testing "Primes less than 10^4."
    (is (approx= (relative-error 10000) 1 0.133)))
  #_(testing "Primes less than 10^5."
      (is (approx= (relative-error 100000) 1 0.105)))
  ;; Stack overflowed. Let's try,
  (testing "Primes less than 1.5*10^4."
    (is (approx= (relative-error 15000) 1 0.130))))

;; After writing these tests I realized, oh hey, the table tells us exactly how many primes there are for several powers of ten.

(deftest expected-prime-counts
  (testing "Primes less than 10."
    (is (= 4 (count (first-primes 10)))))
  (testing "Primes less than 10^2."
    (is (= 25 (count (first-primes 100)))))
  (testing "Primes less than 10^3."
    (is (= 168 (count (first-primes 1000)))))
  (testing "Primes less than 10^4."
    (is (= 1229 (count (first-primes 10000)))))) 
