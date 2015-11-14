'use strict';

angular.module('omsApp')
	.controller('NoteSetDeleteController', function($scope, $modalInstance, entity, NoteSet) {

        $scope.noteSet = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            NoteSet.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });