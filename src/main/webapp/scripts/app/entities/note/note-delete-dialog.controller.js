'use strict';

angular.module('omsApp')
	.controller('NoteDeleteController', function($scope, $modalInstance, entity, Note) {

        $scope.note = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Note.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });