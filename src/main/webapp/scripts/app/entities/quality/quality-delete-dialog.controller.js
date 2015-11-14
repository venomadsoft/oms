'use strict';

angular.module('omsApp')
	.controller('QualityDeleteController', function($scope, $modalInstance, entity, Quality) {

        $scope.quality = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Quality.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });