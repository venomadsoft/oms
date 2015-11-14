'use strict';

angular.module('omsApp')
	.controller('MillDeleteController', function($scope, $modalInstance, entity, Mill) {

        $scope.mill = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Mill.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });