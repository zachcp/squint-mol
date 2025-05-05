(ns mvsj.macros)

(defmacro nodize
  "Transforms a hiccup-like vector [:name props children] into a node map."
  [form]
  (let [name-sym (gensym "name")
        props-sym (gensym "props")
        children-sym (gensym "children")]
    `(let [[~name-sym ~props-sym ~children-sym] ~form]
       (cond-> {"kind" (name ~name-sym)}
         (not (empty? ~props-sym)) (assoc "params" ~props-sym)
         (seq ~children-sym) (assoc "children" (mapv nodize ~children-sym))))))
