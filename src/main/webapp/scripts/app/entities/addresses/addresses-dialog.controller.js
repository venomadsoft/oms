'use strict';

angular.module('omsApp').controller('AddressesDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Addresses', 'Address',
        function($scope, $stateParams, $uibModalInstance, entity, Addresses, Address) {

        $scope.addresses = entity;
        $scope.addresss = Address.query();
        $scope.load = function(id) {
            Addresses.get({id : id}, function(result) {
                $scope.addresses = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:addressesUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.addresses.id != null) {
                Addresses.update($scope.addresses, onSaveSuccess, onSaveError);
            } else {
                Addresses.save($scope.addresses, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
