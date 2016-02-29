'use strict';

angular.module('omsApp')
	.controller('NoteTypeDeleteController', function($scope, $uibModalInstance, entity, NoteType) {

        $scope.noteType = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            NoteType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
