'use strict';

angular.module('omsApp')
	.controller('PriceListDeleteController', function($scope, $modalInstance, entity, PriceList) {

        $scope.priceList = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PriceList.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });