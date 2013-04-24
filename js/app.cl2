(import! "../node_modules/angular-cl2/lib/angular.cl2")

(defapp phonecat [ngResource])

(defcontroller PhoneListCtrl
  "Description goes here!"
  [$scope Phone]
  (set! (-> $scope :phones) (.. Phone query))
  (set! (-> $scope :orderProp) "age"))

(defcontroller PhoneDetailCtrl
  "Docstring rocks!"
  [$scope $routeParams Phone]
  (set!
   (-> $scope :phone)
   (..
    Phone
    (get
     {:phoneId (-> $routeParams :phoneId)}
     (fn
       [phone]
       (set! (-> $scope :mainImageUrl) (get (-> phone :images) 0))
       ))))
  (set!
   (-> $scope :setImage)
   (fn
     [imageUrl]
     (set! (-> $scope :mainImageUrl) imageUrl))))

(deffilter checkmark []
  "Docstring go here."
  [input]
  (if input "✓" "✘"))

(defroute phonecat ;; app-name is optional
  "/phones"
  ['PhoneListCtrl "partials/phone-list.html"]
  "/phones/:phoneId"
  ['PhoneDetailCtrl "partials/phone-detail.html"]
  :default "/phones")

(defmodule phonecat
  (:route
   "/phones"
   ['PhoneListCtrl "partials/phone-list.html"]
   "/phones/:phoneId"
   ['PhoneDetailCtrl "partials/phone-detail.html"]
   :default "/phones"))

(deffactory Phone
  [$resource]
  ($resource
   "phones/:phoneId.json"
   {}
   {:query
    {:isArray true, :params {:phoneId "phones"}, :method "GET"}}))