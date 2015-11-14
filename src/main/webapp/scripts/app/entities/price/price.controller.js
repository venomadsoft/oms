'use strict';

angular.module('omsApp')
    .controller('PriceController', function ($scope, $state, $modal, Price, ParseLinks) {
      
        $scope.prices = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Price.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.prices = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.price = {
                value: null,
                id: null
            };
        };
    });
