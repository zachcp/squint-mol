(ns mvsj.tests
  (:require
   [mvsj.core :as molview]))

(= 1 1)
;; npx squint compile
;; node dist/nextjournal/clojure_mode_tests.mjs

;; (defn deep-log [obj]
;;   (util/inspect obj {:showHidden false :depth nil}))

;; ;; Helper function to log test results
;; (defn log-test [name result]
;;   (if result
;;     (js/console.log (str "✅ PASS: " name))
;;     (js/console.error (str "❌ FAIL: " name))))

;; ;; Helper function to validate against schema
;; (defn validate-json [data test-name]
;;   (let [ajv (doto (new Ajv {:allErrors true :strict false}) (addFormats))
;;         schema-json (.readFileSync fs "molviewspec-v1-openapi-schema.json" "utf8")
;;         schema (js/JSON.parse schema-json)
;;         validate (.compile ajv schema)
;;         valid (.call validate nil data)]
;;     (if valid
;;       (do
;;         (js/console.log (str "✅ " test-name " - JSON is valid!"))
;;         true)
;;       (do
;;         (js/console.error (str "❌ " test-name " - Validation errors:") (.-errors validate))
;;         false))))

;; ;; --- Test Cases ---

;; ;; Test 1: Basic single state with minimal data
;; (defn test-minimal-single-state []
;;   (let [data (/single-state
;;               (/global-metadata
;;                {:title "Minimal MVS"})
;;               (/root []))]
;;     ;; (println data)
;;     (validate-json data "Minimal Single State")))

;; ;; Test 2: Basic primitives
;; (defn test-basic-primitives []
;;   (let [data (/single-state
;;               (/global-metadata
;;                {:title "Basic Primitives Test"
;;                 :description "Testing various primitives"})
;;               (/root
;;                [(/primitives
;;                  {:color :blue
;;                   :tooltip "Simple primitives"
;;                   :ref "primitives-test"}
;;                  [(/tube-primitive {:start [0 0 0] :end [5 5 5] :radius 0.3})
;;                   (/arrow-primitive {:start [0 0 0] :end [0 5 0] :color :green})
;;                   (/box-primitive {:center [2.5 2.5 0] :extent [5 5 1]})
;;                   (/label-primitive {:position [0 0 5] :text "Origin" :label_color :red})])]))]
;;     (println data)
;;     (println (deep-log data))
;;     (validate-json data "Basic Primitives")))

;; ;; Test 3: Complex structure with multiple components
;; (defn test-complex-structure []
;;   (let [data (/single-state
;;               (/global-metadata
;;                {:title "Complex Structure Test"
;;                 :description "Testing a structure with multiple components"
;;                 :description_format :markdown})
;;               (/root
;;                [(/download
;;                  {:url "https://files.rcsb.org/download/4hhb.cif" :ref "download-4hhb"}
;;                  (/parse
;;                   {:format :mmcif :ref "parse-4hhb"}
;;                   (/structure
;;                    {:type :model :ref "struct-4hhb"}
;;                    [(/component
;;                      {:selector :polymer :ref "polymer-component"}
;;                      [(/cartoon-representation {:size_factor 1.2} nil)
;;                       (/color {:color :blue} nil)])
;;                     (/component
;;                      {:selector :ligand :ref "ligand-component"}
;;                      [(/ball-and-stick-representation {:size_factor 1.5} nil)
;;                       (/color {:color :red} nil)])
;;                     (/component
;;                      {:selector :water :ref "water-component"}
;;                      [(/spacefill-representation {:size_factor 0.7} nil)
;;                       (/color {:color :lightblue} nil)])])))]))]
;;     (validate-json data "Complex Structure")))

;; ;; ;; Test 4: Multiple states with snapshots
;; ;; (defn test-multiple-states []
;; ;;   (let [snapshot1 (mvsj/snapshot
;; ;;                    (mvsj/snapshot-metadata
;; ;;                     {:title "First View"
;; ;;                      :description "View from the front"
;; ;;                      :key "front"})
;; ;;                    (mvsj/root
;; ;;                     [(mvsj/camera {:target [0 0 0]
;; ;;                                    :position [0 0 100]
;; ;;                                    :up [0 1 0]})
;; ;;                      (mvsj/download
;; ;;                       {:url "https://files.rcsb.org/download/1cbs.cif" :ref "download-1cbs"}
;; ;;                       (mvsj/parse
;; ;;                        {:format :mmcif :ref "parse-1cbs"}
;; ;;                        (mvsj/structure
;; ;;                         {:type :model :ref "struct-1cbs"}
;; ;;                         [(mvsj/component
;; ;;                           {:selector :all}
;; ;;                           [(mvsj/cartoon-representation {} nil)])]))]))

;; ;;         snapshot2 (mvsj/snapshot
;; ;;                    (mvsj/snapshot-metadata
;; ;;                     {:title "Second View"
;; ;;                      :description "View from the side"
;; ;;                      :key "side"})
;; ;;                    (mvsj/root
;; ;;                     [(mvsj/camera {:target [0 0 0]
;; ;;                                    :position [100 0 0]
;; ;;                                    :up [0 1 0]})
;; ;;                      (mvsj/download
;; ;;                       {:url "https://files.rcsb.org/download/1cbs.cif" :ref "download-1cbs"}
;; ;;                       (mvsj/parse
;; ;;                        {:format :mmcif :ref "parse-1cbs"}
;; ;;                        (mvsj/structure
;; ;;                         {:type :model :ref "struct-1cbs"}
;; ;;                         [(mvsj/component
;; ;;                           {:selector :all}
;; ;;                           [(mvsj/cartoon-representation {} nil)
;; ;;                            (mvsj/color {:color :green} nil)])]))]))

;; ;;         data (mvsj/multiple-states
;; ;;               (mvsj/global-metadata
;; ;;                {:title "Multiple States Example"
;; ;;                 :description "Testing multiple snapshots"})
;; ;;               [snapshot1 snapshot2])]

;; ;;     (validate-json data "Multiple States")))

;; ;; Test 5: Test from test_data.cljs
;; (defn test-existing-example []
;;   (validate-json test-data/mvs-01 "Existing Example from test_data"))

;; ;; Test 6: Testing component expressions with atoms
;; (defn test-component-expressions []
;;   (let [data (/single-state
;;               (/global-metadata
;;                {:title "Component Expressions Test"})
;;               (/root
;;                [(/download
;;                  {:url "https://files.rcsb.org/download/1ubq.cif" :ref "download-1ubq"}
;;                  (/parse
;;                   {:format :mmcif :ref "parse-1ubq"}
;;                   (/structure
;;                    {:type :model :ref "struct-1ubq"}
;;                    [(/primitives
;;                      {:color :red :ref "atom-connections"}
;;                      [(/distance-measurement-primitive
;;                        {:start (/component {:auth_atom_id "CA" :auth_seq_id 1 :auth_asym_id "A"} nil)
;;                         :end (/component {:auth_atom_id "CA" :auth_seq_id 76 :auth_asym_id "A"} nil)
;;                         :label_template "Distance: {{distance}} Å"})
;;                       (/tube-primitive
;;                        {:start (/component {:auth_atom_id "CA" :auth_seq_id 30 :auth_asym_id "A"} nil)
;;                         :end (/component {:auth_atom_id "CA" :auth_seq_id 40 :auth_asym_id "A"} nil)
;;                         :radius 0.3
;;                         :color :blue})])])))]))]
;;     (validate-json data "Component Expressions")))

;; ;; Test 7: Testing transforms and complex scene hierarchy
;; (defn test-transforms []
;;   (let [data (/single-state
;;               (/global-metadata
;;                {:title "Transforms Test"})
;;               (/root
;;                [(/transform
;;                  {:rotation [1 0 0 0 1 0 0 0 1]
;;                   :translation [10 0 0]}
;;                  [(/primitives
;;                    {:ref "transformed-primitives"}
;;                    [(/box-primitive {:center [0 0 0] :extent [5 5 5] :face_color :blue})
;;                     (/label-primitive {:position [0 0 3] :text "Transformed Box"})])])
;;                 (/primitives
;;                  {:ref "untransformed-primitives"}
;;                  [(/box-primitive {:center [0 0 0] :extent [5 5 5] :face_color :red})
;;                   (/label-primitive {:position [0 0 3] :text "Original Box"})])]))]
;;     (validate-json data "Transforms")))

;; ;; Run all tests
;; (defn run-all-tests []
;;   (js/console.log "=== Running MVSJ Tests ===")
;;   (log-test "Minimal Single State" (test-minimal-single-state))
;;   (log-test "Basic Primitives" (test-basic-primitives))
;;   (log-test "Complex Structure" (test-complex-structure))
;;   ;; (log-test "Multiple States" (test-multiple-states))
;;   (log-test "Existing Example" (test-existing-example))
;;   (log-test "Component Expressions" (test-component-expressions))
;;   (log-test "Transforms" (test-transforms))
;;   (js/console.log "=== Test Suite Complete ==="))

;; ;; Automatically run tests when this file is executed
;; ;; (run-all-tests)

;; ;; (test-minimal-single-state)
;; (test-basic-primitives)
