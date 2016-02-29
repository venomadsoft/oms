'use strict';

angular.module('omsApp').controller('NoteTypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'NoteType',
        function($scope, $stateParams, $uibModalInstance, entity, NoteType) {

        $scope.noteType = entity;
        $scope.load = function(id) {
            NoteType.get({id : id}, function(result) {
                $scope.noteType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:noteTypeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.noteType.id != null) {
                NoteType.update($scope.noteType, onSaveSuccess, onSaveError);
            } else {
                NoteType.save($scope.noteType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
