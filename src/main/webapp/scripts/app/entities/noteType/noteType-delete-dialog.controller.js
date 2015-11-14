'use strict';

angular.module('omsApp')
	.controller('NoteTypeDeleteController', function($scope, $modalInstance, entity, NoteType) {

        $scope.noteType = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            NoteType.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });