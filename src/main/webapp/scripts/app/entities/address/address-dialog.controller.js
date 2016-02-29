'use strict';

angular.module('omsApp').controller('AddressDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Address', 'Addresses', 'AddressLine',
        function($scope, $stateParams, $uibModalInstance, entity, Address, Addresses, AddressLine) {

        $scope.address = entity;
        $scope.addressess = Addresses.query();
        $scope.addresslines = AddressLine.query();
        $scope.load = function(id) {
            Address.get({id : id}, function(result) {
                $scope.address = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:addressUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.address.id != null) {
                Address.update($scope.address, onSaveSuccess, onSaveError);
            } else {
                Address.save($scope.address, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
