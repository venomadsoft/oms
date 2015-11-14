'use strict';

angular.module('omsApp')
    .controller('CustomerGroupController', function ($scope, $state, $modal, CustomerGroup, ParseLinks) {
      
        $scope.customerGroups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CustomerGroup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.customerGroups = result;
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
            $scope.customerGroup = {
                code: null,
                name: null,
                freight: null,
                id: null
            };
        };
    });
