(ns mvsj.tests
  (:require [mvsj.core :as mcore]
            [mvsj.macros :refer [nodize]]))

(assert (=
         (str (nodize [:test {:param 1} []]))
         (str {:kind "test" :params {:param 1}})))

(assert (=
         (str (nodize [:test {:param 1} [[:test2 {:param 2}]]]))
         (str {:kind "test" :params {:param 1} :children [{:kind "test2" :params {:param 2}}]})))

;; npx squint compile
;; node dist/nextjournal/clojure_mode_tests.mjs
