(ns sashton.tools.ns-tools)

(defmacro with-ns
  "Evals the body in the provided namespace"
  [target-ns & body]
  `(binding [*ns* (the-ns ~target-ns)]
     ~@(mapv (fn [form] `(eval '~form)) body)))

