'use strict';

angular.module('omsApp')
	.controller('QualityDeleteController', function($scope, $uibModalInstance, entity, Quality) {

        $scope.quality = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Quality.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
