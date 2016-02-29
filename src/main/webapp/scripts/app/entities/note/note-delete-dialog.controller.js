'use strict';

angular.module('omsApp')
	.controller('NoteDeleteController', function($scope, $uibModalInstance, entity, Note) {

        $scope.note = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Note.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
