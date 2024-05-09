# Extra Collections [![](https://jitpack.io/v/Col-E/ExtraCollections.svg)](https://jitpack.io/#Col-E/ExtraCollections) ![](https://github.com/Col-E/ExtraCollections/actions/workflows/display_test_results.yml/badge.svg)

Handy utilities for things I wish were in Java's collections api.

## Features

- Delegating collections
- Observable collections
- Invertible Map (BiMap)
- Boxes
  - Object `T`
  - `int`
  - `long`
  - `float`
  - `double`
  - `char`
- Trees
  - Treated as additions to `Map<K, Tree<K,V>>`
- Tuples
  - Pair
- Lambdas that `throw Throwable`
- Common utilities for `List`, `Map`, and `Set` types with features such as:
  - Creating singleton collections
  - Creating disjoint & union collection of two input collections