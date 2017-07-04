(ns twitter-example.core
 (:require [clojure.set]
           [clojure.string :as s]
           [clj-http.client :as client]
           [overtone.at-at :as overtone]
           [twitter.api.restful :as twitter]
           [twitter.oauth :as twitter-oauth]
           [net.cgrand.enlive-html :as html]
           [environ.core :refer [env]]))

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn construct-url []
  (str "http://www.goodreads.com/quotes/tag/existentialism" "?page=" (+ (rand-int 23) 1)))

(defn get-quotes []
  (map html/text (html/select (fetch-url (construct-url)) [:div.quoteText html/text])))

(defn script? [q]
  (not (or (s/includes? q "<") (s/includes? q ">"))))

(defn filter-for-string [strs]
  (filter string? strs))

(defn trim-strings [strs]
  (map s/trim strs))

(defn filter-for-scripts [strs]
  (filter script? strs))

(defn quoteish? [str]
  (and (> (.length str) 55) (< (.length str) 120)))

(defn filter-quotes [quotes]
  (-> quotes
    (filter-for-string)
    (trim-strings)
    (filter-for-scripts)
    (#(filter quoteish? %))))

(def snarks ["Really?"
             "I'd like to believe that."
             "Oh, the dogity."
             "Ponder that."
             "Interesting."
             "I have to research that."
             "Woof."
             "Why, though?"
             "What a conundrum."
             "Bark..."
             "Understandable."
             "Eventually."])

(defn generate-remark-on-reality []
  (let [template (rand-nth (filter-quotes (get-quotes)))
        snark (rand-nth snarks)]
      (str template " " snark)))


; We retrieve the twitter credentials from the profiles.clj file here.
; In profiles.clj we defined the "env(ironment)" which we use here
; to get the secret passwords we need.
; Make sure you add your credentials in profiles.clj and not here!
(def twitter-credentials (twitter-oauth/make-oauth-creds (env :app-consumer-key)
                                                         (env :app-consumer-secret)
                                                         (env :user-access-token)
                                                         (env :user-access-secret)))

; Tweets are limited to 140 characters. We might randomly generate a sentence
; with more than 140 characters, which would be rejected by Twitter.
; So we check if our generated sentence is longer than 140 characters, and
; don't tweet if so.
(defn tweet [text]
  (when (and (not-empty text) (<= (count text) 140))
   (try (twitter/statuses-update :oauth-creds twitter-credentials
                                 :params {:status text})
        (catch Exception e
          (do
            (println "Something went wrong: " (.getMessage e))
            (.printStackTrace e))))))

; Generate a sentence and tweet it.
(defn tweet-sentence []
  (tweet (generate-remark-on-reality)))

(def my-pool (overtone/mk-pool))

(defn -main [& args]
  ;; every 2 hours (* 1000 60 60 2)
  ;; every 2 minutes (* 1000 60 2)
  (println "Started up")
  (overtone/every (* 1000 60 2) #(println (tweet-sentence)) my-pool))
