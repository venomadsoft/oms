'use strict';

angular.module('omsApp')
	.controller('LineDeleteController', function($scope, $modalInstance, entity, Line) {

        $scope.line = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Line.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });