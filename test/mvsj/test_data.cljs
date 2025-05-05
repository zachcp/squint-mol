(ns mvsj.test-data
  (:require
   [mvsj.mvsj :as mvsj]
   ["fs" :as fs]
   ["ajv$default" :as Ajv]
   ["ajv-formats$default" :as addFormats]))

(defn- fetch-schema []
  (let [schema-json (.readFileSync fs "molviewspec-v1-openapi-schema.json" "utf8")]
    (js/JSON.parse schema-json)))

;; Validate data against the schema
(defn validate-data [data]
  (let [ajv (doto (new Ajv {:allErrors true :strict false}) (addFormats))
        schema (fetch-schema)
        validate (.compile ajv schema)
        valid (.call validate nil data)]
    (if valid
      (js/console.log "✅ JSON is valid!")
      (js/console.error "❌ Validation errors:" (.-errors validate)))))

;; (def example-01
;;   (mvsj/single-state
;;   (mvsj/global-metadata {})
;;   (mvsj/root {}
;;     [(mvsj/download
;;       {:url "https://www.ebi.ac.uk/pdbe/entry-files/download/1cbs_updated.cif"}
;;       (mvsj/parse {:format :mmcif :ref "parse-1kim"}
;;         []
;;   )

;; (defmacro nodize
;;   "Transforms a hiccup-like vector [:name props children] into a node map."
;;   [form]
;;   (let [name-sym (gensym "name")
;;         props-sym (gensym "props")
;;         children-sym (gensym "children")]
;;     `(let [[~name-sym ~props-sym ~children-sym] ~form]
;;        (cond-> {"kind" (name ~name-sym)}
;;          (not (empty? ~props-sym)) (assoc "params" ~props-sym)
;;          (seq ~children-sym) (assoc "children" (mapv nodize ~children-sym))))))

;; (def mvs-01
;;   (mvsj/single-state
;;    (mvsj/global-metadata
;;     {:title "My First MVS (Spec Validated)"
;;      :description "Example structure visualization."})
;;    (mvsj/root
;;     [(mvsj/download
;;       {:url "https://files.rcsb.org/download/1kim.mmcif" :ref "download-1kim"}
;;       (mvsj/parse
;;        {:format :mmcif :ref "parse-1kim"}
;;        (mvsj/structure
;;         {:type :model :assembly_index 0 :ref "struct-1kim"}
;;         [(mvsj/component
;;           {:selector :polymer :ref "polymer-component"}
;;           [(mvsj/cartoon-representation {} nil)
;;            (mvsj/spacefill-representation {:ignore_hydrogens true} nil)])
;;          (mvsj/primitives
;;           {:color :red
;;            :tooltip "Some primitives"
;;            :ref "some-primitives"}
;;           [(mvsj/tube-primitive {:start [0 0 0] :end [10 0 0] :radius 0.5 :dash_length 1.0})
;;            (mvsj/label-primitive {:position [5 0.5 0]  :ref "x-label"})
;;            (mvsj/arrow-primitive {:start [0 0 0] :end [0 10 0] :color "#00FF00"})
;;            (mvsj/distance-measurement-primitive
;;             {:start (mvsj/component {:auth_atom_id "CA" :auth_seq_id 10 :auth_asym_id "A"} nil)
;;              :end (mvsj/component {:auth_atom_id "CA" :auth_seq_id 20 :auth_asym_id "A"} nil)
;;              :label_template "Distance: {{distance}} Angstrom"})])])))])))
