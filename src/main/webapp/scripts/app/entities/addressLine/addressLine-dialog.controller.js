'use strict';

angular.module('omsApp').controller('AddressLineDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'AddressLine', 'Address',
        function($scope, $stateParams, $uibModalInstance, entity, AddressLine, Address) {

        $scope.addressLine = entity;
        $scope.addresss = Address.query();
        $scope.load = function(id) {
            AddressLine.get({id : id}, function(result) {
                $scope.addressLine = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:addressLineUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.addressLine.id != null) {
                AddressLine.update($scope.addressLine, onSaveSuccess, onSaveError);
            } else {
                AddressLine.save($scope.addressLine, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
