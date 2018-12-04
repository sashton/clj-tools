(ns sashton.tools.repl-tools
  (:require [sashton.tools.ns-tools]))

(def saved-form (atom nil))

(defn clear-saved-form!
  []
  (reset! saved-form nil))

(defn set-saved-form!
  [ns form]
  (println ns (type ns))
  (let [form' `(sashton.tools.ns-tools/with-ns '~ns ~form)]
    (reset! saved-form form')))

(defn ^:private parsed-int
  [s]
  (cond
    (int? s) s
    (string? s) (try
                  (Integer/parseInt s)
                  (catch Exception _ nil))))

(defn set-saved-letscope-form!
  [ns form]
  (when-let [exec-point (parsed-int (read-line))]
    (let [form' `(sc.api/letsc ~exec-point ~form)]
      (set-saved-form! ns form'))))

(defn run-saved-form
  []
  (if-let [form @saved-form]
    (eval form)
    (println "No saved-form available to run")))
