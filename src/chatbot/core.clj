
(ns chatbot.core)

; data

(def stramovka {"hours" "Open all year around"
                "playground" "Yes"
                "refreshments" "Yes"
                "food" "No"
                "dogs" "Yes"
                "restaurant" "Yes"
                "biking" "Yes"
                "skating" "Yes"
                "toilet" "Yes"
                "attractions" "zoo, botanical gardens, natural attractions, planetarium, exhibition grounds"})

(def parks{"stramovka" stramovka})

(def users ['Jeremy', 'Sami', 'Maxim'])

; helper functions 
(defn read-line-with-prompt [prompt]
  (println prompt)
  (read-line))

(defn exit-now [word]
  (if (or (= word "end") (= word "bye"))
    (System/exit 0)))

(defn remove-punctuation [word]
  (clojure.string/replace word #"(?i)[^\w']+" " ")
  (clojure.string/lower-case word))

; main/answering user questions
(defn select-park [parks]
  (parks (remove-punctuation (read-line-with-prompt "I've information about Stromovka."))))

(defn parsing-questions [park]
  (def counter (atom 1))
  (doseq [line (repeatedly #(read-line-with-prompt "What do you want to know about the park? ")) :while line]
    (exit-now line)
    (reset! counter 1)
    (doseq [A (clojure.string/split (remove-punctuation line) #" ")]
      (let [result (park A)]
        (if result
          (println "Yes, I have information about" A "! It's: " result))
        (if (= @counter (count (clojure.string/split (remove-punctuation line) #" ")))
          (if-not result
            (println "Sorry, I have no information about it:" A))))
      (swap! counter inc))))

(defn -main [& args]

; user greeting part
  (let [username (read-line-with-prompt "Hello. What is your name? ")]
    (if (= (some #(= username %) users) true)
      (println "Welcome back " username "! How are you?")
      (println "Welcome " username "! How are you?")))

; parsing user input and providing them with answers
  (parsing-questions (select-park parks)))
