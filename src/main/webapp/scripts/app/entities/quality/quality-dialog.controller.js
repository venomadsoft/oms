'use strict';

angular.module('omsApp').controller('QualityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Quality', 'Mill',
        function($scope, $stateParams, $modalInstance, entity, Quality, Mill) {

        $scope.quality = entity;
        $scope.mills = Mill.query();
        $scope.load = function(id) {
            Quality.get({id : id}, function(result) {
                $scope.quality = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:qualityUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.quality.id != null) {
                Quality.update($scope.quality, onSaveSuccess, onSaveError);
            } else {
                Quality.save($scope.quality, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
