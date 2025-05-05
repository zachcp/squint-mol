(ns mvsj.mvsj)

;; -- Enum Values (as keywords) -----------------------------------------------
(def node-kinds
  #{:root :camera :canvas :color :color_from_source
    :color_from_uri :component :component_from_source
    :component_from_uri :download :focus :label
    :label_from_source :label_from_uri :mesh_from_source
    :mesh_from_uri :opacity :parse :primitives
    :primitives_from_uri :primitive :representation
    :structure :tooltip :tooltip_from_source :tooltip_from_uri
    :transform :volume :volume_representation})

(def color-names #{:aliceblue :antiquewhite :aqua :aquamarine :azure
                   :beige :bisque :black :blanchedalmond :blue
                   :blueviolet :brown :burlywood :cadetblue :chartreuse
                   :chocolate :coral :cornflowerblue :cornsilk :crimson
                   :cyan :darkblue :darkcyan :darkgoldenrod :darkgray
                   :darkgreen :darkgrey :darkkhaki :darkmagenta :darkolivegreen
                   :darkorange :darkorchid :darkred :darksalmon :darkseagreen
                   :darkslateblue :darkslategray :darkslategrey :darkturquoise
                   :darkviolet :deeppink :deepskyblue :dimgray :dimgrey
                   :dodgerblue :firebrick :floralwhite :forestgreen :fuchsia
                   :gainsboro :ghostwhite :gold :goldenrod :gray :green
                   :greenyellow :grey :honeydew :hotpink :indianred :indigo
                   :ivory :khaki :lavender :lavenderblush :lawngreen
                   :lemonchiffon :lightblue :lightcoral :lightcyan
                   :lightgoldenrodyellow :lightgray :lightgreen :lightgrey
                   :lightpink :lightsalmon :lightseagreen :lightskyblue
                   :lightslategray :lightslategrey :lightsteelblue :lightyellow
                   :lime :limegreen :linen :magenta :maroon :mediumaquamarine
                   :mediumblue :mediumorchid :mediumpurple :mediumseagreen
                   :mediumslateblue :mediumspringgreen :mediumturquoise
                   :mediumvioletred :midnightblue :mintcream :mistyrose
                   :moccasin :navajowhite :navy :oldlace :olive :olivedrab
                   :orange :orangered :orchid :palegoldenrod :palegreen
                   :paleturquoise :palevioletred :papayawhip :peachpuff
                   :peru :pink :plum :powderblue :purple :red :rosybrown
                   :royalblue :saddlebrown :salmon :sandybrown :seagreen
                   :seashell :sienna :silver :skyblue :slateblue :slategray
                   :slategrey :snow :springgreen :steelblue :tan :teal
                   :thistle :tomato :turquoise :violet :wheat :white
                   :whitesmoke :yellow :yellowgreen})

(def format-names #{:cif :bcif :json})
(def parse-format-names #{:mmcif :bcif :pdb :map})
(def description-formats #{:markdown :plaintext})
(def structure-types #{:model :assembly :symmetry :symmetry_mates})
(def representation-types #{:ball_and_stick :spacefill :cartoon :surface :isosurface :carbohydrate})
(def volume-representation-types #{:isosurface})
(def component-selectors #{:all :polymer :protein :nucleic :branched :ligand :ion :water :coarse})
(def expression-schemas #{:whole_structure :entity :chain :auth_chain :residue :auth_residue :residue_range :auth_residue_range :atom :auth_atom :all_atomic})
(def primitive-kinds #{:angle_measurement :arrow :box :ellipse :ellipsoid :label :lines :mesh :distance_measurement :tube})

;; -- Helper functions -------------------------------------------------------
(defn- clean-map [m] (into {} (filter (comp some? val) m)))

;; Helper to process point-like parameters (vectors, ComponentExpression maps, PrimitiveComponentExpressions maps)
(defn- process-point-like-param [p]
  (cond
    (map? p)  (clean-map p)
    (vector? p) p
    :else p))

;; Helper to process common primitive params that might contain point-like values or colors
(defn- process-primitive-params [params]
  (-> params
      (update :start process-point-like-param)
      (update :end process-point-like-param)
      (update :a process-point-like-param)
      (update :b process-point-like-param)
      (update :c process-point-like-param)
      (update :center process-point-like-param)
      (update :position process-point-like-param)
      (update :color (fn [c] c))
      (update :face_color (fn [c] c))
      (update :edge_color (fn [c] c))
      (update :label_color (fn [c] c))
      (update :vector_color (fn [c] c))
      (update :section_color (fn [c] c))
      (update :wireframe_color (fn [c] c))
      (update :group_colors (fn [gc] (when gc
                                       (into {} (map (fn [[k v]] [k v]) gc)))))))

;; -- Functional Constructors (Return Maps) -----------------------------------
;; These functions create the standard ClojureScript maps required by the MVS spec.

;; Generic Node structure (mostly for internal use)
(defn- node-map
  "Creates a generic MVS node map. Internal helper."
  [kind params children]
  (clean-map
   {:kind kind
    :params (clean-map params)
    :children children}))

(defn root
  "Creates a root node map"
  [children]
  (node-map :root {} (vec children)))

(defn download
  "Creates a download node map"
  [params children]
  (node-map :download params children))

(defn parse
  "Creates a parse node map."
  [format children]
  (node-map :parse format children))

(defn structure
  "Creates a structure node map"
  [structure-type children]
  (node-map :structure {:type  structure-type} children))

(defn color
  "Creates a color node map. (Defaults to inline params)"
  [color  children]
  (node-map :color color  children))

;; Add constructors for other color sources if needed:
;; (defn color-from-source [params & opts children] ...)
;; (defn color-from-uri [params & opts children] ...)

(defn component
  "Creates a component node map. (Defaults to inline params)"
  [params  children]
  (node-map :component params children))

(defn label
  "Creates a label node map. (Defaults to inline params)"
  [params  children]
  (node-map :label params children)) ; LabelInlineParams only has text

(defn tooltip
  "Creates a tooltip node map. (Defaults to inline params)"
  [params  children]
  (node-map :tooltip params children))

(defn opacity
  "Creates an opacity node map. (Defaults to inline params)"
  [params children]
  (node-map :opacity params children))

(defn representation
  "Creates a representation node map."
  [representation children]
  (node-map :representation {:type  representation} children))

;; Syntactic sugar for common representations
(defn ball-and-stick-representation [params   children] (apply representation (assoc params :type :ball_and_stick)  children))
(defn cartoon-representation [params  children] (apply representation (assoc params :type :cartoon)  children))
(defn spacefill-representation [params  children] (apply representation (assoc params :type :spacefill)  children))
(defn surface-representation [params  children] (apply representation (assoc params :type :surface)  children))
(defn carbohydrate-representation [params  children] (apply representation (assoc params :type :carbohydrate)  children))

(defn primitives
  "Creates a primitives group node map."
  [params children]
  (node-map :primitives params children))

(defn primitive
  "Creates a single primitive node map."
  [kind params]
  (node-map :primitive (assoc (process-primitive-params params) :kind kind) nil))

;; Syntactic sugar for specific primitives
(defn angle-measurement-primitive [params] (primitive :angle_measurement params))
(defn arrow-primitive [params] (primitive :arrow params))
(defn box-primitive [params] (primitive :box params))
(defn ellipse-primitive [params] (primitive :ellipse params))
(defn ellipsoid-primitive [params] (primitive :ellipsoid params))
(defn label-primitive [params] (primitive :label params))
(defn lines-primitive [params] (primitive :lines params))
(defn mesh-primitive [params] (primitive :mesh params))
(defn distance-measurement-primitive [params] (apply primitive :distance_measurement params))
(defn tube-primitive [params] (primitive :tube params))

(defn camera
  "Creates a camera node map."
  [params]
  (node-map :camera params nil))

(defn canvas
  "Creates a canvas node map."
  [params]
  (node-map :canvas params nil))

(defn focus-inline
  "Creates a focus node map with inline params."
  [params]
  (node-map :focus params nil))

(defn transform
  "Creates a transform node map."
  [params children]
  (node-map :transform params children))

(defn volume
  "Creates a volume node map."
  [params   children]
  (node-map :volume params children))

(defn volume-representation
  "Creates a volume representation node map."
  [params  children]
  (node-map :representation (update params :type :volume) children))

;; Syntactic sugar for volume representations
(defn isosurface-representation
  "Creates an 'isosurface' volume representation node map."
  [params  children]
  (apply volume-representation (assoc params :type :isosurface)  children))

;; -- Top-Level State Constructors (Return Maps/Vectors of Maps) --------------

(defn global-metadata
  "Creates a GlobalMetadata map.

  Args:
  - params: Optional map of global metadata parameters."
  [params]
  (clean-map params))

(defn snapshot-metadata
  "Creates a SnapshotMetadata map."
  [params]

  (clean-map params))

(defn snapshot
  "Creates a snapshot map for use in a multi-state.

  Args:
  - metadata: Map of snapshot metadata (created with `snapshot-metadata`). Required.
  - root-node: The root node map of the snapshot tree (created with `root`). Required."
  [metadata root-node]
  (clean-map
   {:metadata metadata
    :root root-node}))

(defn single-state
  "Creates a single MVS state map.

  Args:
  - metadata: Map of global metadata (created with `global-metadata`). Optional.
  - root-node: The root node map of the state tree (created with `root`). Required."
  [metadata root-node]
  (clean-map
   {:kind "single"
    :metadata metadata
    :root root-node}))

(defn multiple-states
  "Creates a multiple MVS states map.

  Args:
  - metadata: Map of global metadata (created with `global-metadata`). Optional.
  - snapshots: Vector of snapshot maps (created with `snapshot`). Required."
  [metadata snapshots]
  (clean-map
   {:kind "multiple"
    :metadata metadata
    :snapshots (vec snapshots)}))
;;
