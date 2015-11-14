'use strict';

angular.module('omsApp')
	.controller('DerivedGsmShadeDeleteController', function($scope, $modalInstance, entity, DerivedGsmShade) {

        $scope.derivedGsmShade = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DerivedGsmShade.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });