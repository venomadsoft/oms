'use strict';

angular.module('omsApp')
	.controller('MillDeleteController', function($scope, $uibModalInstance, entity, Mill) {

        $scope.mill = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Mill.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
