(ns core
  (:require
   [mvsj :as mvsj]
   [test_data :as tests]))

;; (comment

(tests/validate-data tests/mvs-01)

(tests/validate-data
 (mvsj/single-state
  (mvsj/global-metadata {})
  (mvsj/root
   (mvsj/download {} nil))))

  ;; )
