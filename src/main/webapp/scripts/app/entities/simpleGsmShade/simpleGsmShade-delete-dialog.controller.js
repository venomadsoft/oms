'use strict';

angular.module('omsApp')
	.controller('SimpleGsmShadeDeleteController', function($scope, $uibModalInstance, entity, SimpleGsmShade) {

        $scope.simpleGsmShade = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SimpleGsmShade.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
