(ns mvsj.core
  (:require
   [mvsj.mvsj :as mvsj]
   ["fs" :as fs]
   ["ajv$default" :as Ajv]
   ["ajv-formats$default" :as addFormats])
  (:require-macros [mvsj.macros :refer [nodize]]))

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

(let [node_01 (nodize [:node {} []])])
