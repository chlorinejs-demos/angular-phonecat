(import! "../node_modules/angular-cl2/lib/angular.cl2")

(defmodule (phonecat [ngResource])
  (:route
   (defroute
     "/phones"
     ['PhoneListCtrl "partials/phone-list.html"]
     "/phones/:phoneId"
     ['PhoneDetailCtrl "partials/phone-detail.html"]
     :default "/phones"))
  (:controller
   (PhoneListCtrl
    "Description goes here!"
    [$scope Phone]
    (set! (-> $scope :phones) (.. Phone query))
    (set! (-> $scope :orderProp) "age"))
   (PhoneDetailCtrl
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
       (set! (-> $scope :mainImageUrl) imageUrl)))))
  (:filter
   (checkmark [] [input] (if input "✓" "✘")))
  (:factory
   (Phone
    [$resource]
    ($resource
     "phones/:phoneId.json"
     {}
     {:query
      {:isArray true, :params {:phoneId "phones"}, :method "GET"}}))))
