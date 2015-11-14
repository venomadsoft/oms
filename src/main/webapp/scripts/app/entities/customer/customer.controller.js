'use strict';

angular.module('omsApp')
    .controller('CustomerController', function ($scope, $state, $modal, Customer, ParseLinks) {
      
        $scope.customers = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Customer.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.customers = result;
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
            $scope.customer = {
                code: null,
                name: null,
                id: null
            };
        };
    });
