'use strict';

angular.module('omsApp').controller('NoteSetDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'NoteSet', 'Note',
        function($scope, $stateParams, $uibModalInstance, entity, NoteSet, Note) {

        $scope.noteSet = entity;
        $scope.notes = Note.query();
        $scope.load = function(id) {
            NoteSet.get({id : id}, function(result) {
                $scope.noteSet = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:noteSetUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.noteSet.id != null) {
                NoteSet.update($scope.noteSet, onSaveSuccess, onSaveError);
            } else {
                NoteSet.save($scope.noteSet, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
