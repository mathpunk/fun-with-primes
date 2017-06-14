(ns fun-with-primes.core
  (:gen-class))

(defn is-multiple? [modulus number]
  (when (or (not (integer? modulus))
            (= modulus 0))
    (throw (Exception. "Modulus must be an integer greater than zero.")))
  (integer? (/ number modulus)))

(defn sieve
  [modulus numbers]
  (remove (partial is-multiple? modulus) numbers))

(defn first-primes [n]
  (loop [primes (transient [])
         candidates (range 2 n)]
    (if (empty? candidates)
      (persistent! primes)
      (let [next-prime (first candidates)
            next-candidates (sieve next-prime candidates)]
        (recur (conj! primes next-prime)
               next-candidates)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (first-primes args)))
