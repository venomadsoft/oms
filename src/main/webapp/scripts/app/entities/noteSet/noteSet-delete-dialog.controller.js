'use strict';

angular.module('omsApp')
	.controller('NoteSetDeleteController', function($scope, $uibModalInstance, entity, NoteSet) {

        $scope.noteSet = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            NoteSet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
