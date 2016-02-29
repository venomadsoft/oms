'use strict';

angular.module('omsApp')
	.controller('LineDeleteController', function($scope, $uibModalInstance, entity, Line) {

        $scope.line = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Line.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
