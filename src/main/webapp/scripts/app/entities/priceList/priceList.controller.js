'use strict';

angular.module('omsApp')
    .controller('PriceListController', function ($scope, $state, $modal, PriceList, ParseLinks) {
      
        $scope.priceLists = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PriceList.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.priceLists = result;
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
            $scope.priceList = {
                wefDateFrom: null,
                wefDateTo: null,
                active: null,
                id: null
            };
        };
    });
