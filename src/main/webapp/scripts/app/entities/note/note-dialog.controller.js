'use strict';

angular.module('omsApp').controller('NoteDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Note', 'NoteType', 'Line', 'NoteSet',
        function($scope, $stateParams, $uibModalInstance, entity, Note, NoteType, Line, NoteSet) {

        $scope.note = entity;
        $scope.notetypes = NoteType.query();
        $scope.lines = Line.query();
        $scope.notesets = NoteSet.query();
        $scope.load = function(id) {
            Note.get({id : id}, function(result) {
                $scope.note = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:noteUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.note.id != null) {
                Note.update($scope.note, onSaveSuccess, onSaveError);
            } else {
                Note.save($scope.note, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
