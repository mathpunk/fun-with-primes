## Fun with primes in Clojure

An implementation of the Sieve of Eratosthenes for finite and infinite sequences, for printing a pretty multiplication table of the first n prime numbers.

It takes about 30s on my Intel Core i5 to find the first 300 primes and multiply them out. Surely this could be optimized further.

See also the Sieve of Atkin, a significantly fancier approach (requiring enumeration of the solutions to some quadratic forms) with better asymptotic complexity.

### Usage 

`lein run <integer>` will compute the first <integer> primes, and print out a multiplication table of those primes.

### Tests

The Prime Number Theorem gives us an approximation for how many primes there are less than some given value of `x`. The tests check whether the `primes-less-than` function is finding the right number of primes according to the Prime Number Theorem. This is meant as a proxy test for `first-primes`, which is identical except for the exit test and candidate generator. (Might there be a way to abstract the two functions a little to test the same loop with the different test and sequence?)
