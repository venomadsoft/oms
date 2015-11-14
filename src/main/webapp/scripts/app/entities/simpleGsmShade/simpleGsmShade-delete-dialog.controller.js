'use strict';

angular.module('omsApp')
	.controller('SimpleGsmShadeDeleteController', function($scope, $modalInstance, entity, SimpleGsmShade) {

        $scope.simpleGsmShade = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SimpleGsmShade.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });