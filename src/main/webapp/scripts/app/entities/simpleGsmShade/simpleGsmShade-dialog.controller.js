'use strict';

angular.module('omsApp').controller('SimpleGsmShadeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'SimpleGsmShade', 'Mill',
        function($scope, $stateParams, $modalInstance, entity, SimpleGsmShade, Mill) {

        $scope.simpleGsmShade = entity;
        $scope.mills = Mill.query();
        $scope.load = function(id) {
            SimpleGsmShade.get({id : id}, function(result) {
                $scope.simpleGsmShade = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:simpleGsmShadeUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.simpleGsmShade.id != null) {
                SimpleGsmShade.update($scope.simpleGsmShade, onSaveSuccess, onSaveError);
            } else {
                SimpleGsmShade.save($scope.simpleGsmShade, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
