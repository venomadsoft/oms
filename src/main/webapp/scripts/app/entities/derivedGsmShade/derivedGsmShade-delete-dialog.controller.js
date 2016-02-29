'use strict';

angular.module('omsApp')
	.controller('DerivedGsmShadeDeleteController', function($scope, $uibModalInstance, entity, DerivedGsmShade) {

        $scope.derivedGsmShade = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DerivedGsmShade.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
