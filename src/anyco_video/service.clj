(ns anyco-video.service
    (:require [io.pedestal.http :as bootstrap]
              [io.pedestal.http.route :as route]
              [io.pedestal.http.body-params :as body-params]
              [io.pedestal.http.route.definition :refer [defroutes]]
              [ring.util.response :as ring-resp]
              [hiccup.core :as hiccup]))

(defn comment-page
  [request]
  (ring-resp/response (hiccup/html [:html
                                    [:head
                                     [:script {:src "//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"}]
                                     [:script {:src "/comment.js" :defer true}]]
                                    [:body
                                     [:div
                                      [:label "comment"]]
                                     [:div
                                      [:textarea#comment]]
                                     [:div
                                      [:button#postBtn "post"]]]])))

(defn comment-creation
  [request]
  (ring-resp/response "OK"))

(defn home-page
  [request]
  (ring-resp/response "Hello World!"))

(defroutes routes
  [[["/" {:get home-page}
     ;; Set default interceptors for /about and any other paths under /
     ^:interceptors [(body-params/body-params) bootstrap/html-body]
     ["/comment" {:get comment-page}]
     ["/comment" {:post comment-creation}]]]])

;; Consumed by anyco-video.server/create-server
;; See bootstrap/default-interceptors for additional options you can configure
(def service {:env :prod
              ;; You can bring your own non-default interceptors. Make
              ;; sure you include routing and set it up right for
              ;; dev-mode. If you do, many other keys for configuring
              ;; default interceptors will be ignored.
              ;; :bootstrap/interceptors []
              ::bootstrap/routes routes

              ;; Uncomment next line to enable CORS support, add
              ;; string(s) specifying scheme, host and port for
              ;; allowed source(s):
              ;;
              ;; "http://localhost:8080"
              ;;
              ;;::bootstrap/allowed-origins ["scheme://host:port"]

              ;; Root for resource interceptor that is available by default.
              ::bootstrap/resource-path "/public"

              ;; Either :jetty or :tomcat (see comments in project.clj
              ;; to enable Tomcat)
              ;;::bootstrap/host "localhost"
              ::bootstrap/type :jetty
              ::bootstrap/port 8080})

