'use strict';

angular.module('omsApp')
	.controller('PriceListDeleteController', function($scope, $uibModalInstance, entity, PriceList) {

        $scope.priceList = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PriceList.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
