'use strict';

angular.module('omsApp')
	.controller('FormulaeDeleteController', function($scope, $modalInstance, entity, Formulae) {

        $scope.formulae = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Formulae.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });