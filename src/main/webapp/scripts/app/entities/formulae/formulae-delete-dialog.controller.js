'use strict';

angular.module('omsApp')
	.controller('FormulaeDeleteController', function($scope, $uibModalInstance, entity, Formulae) {

        $scope.formulae = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Formulae.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
