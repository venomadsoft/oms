'use strict';

angular.module('omsApp').controller('AddressesDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Addresses', 'Address',
        function($scope, $stateParams, $modalInstance, entity, Addresses, Address) {

        $scope.addresses = entity;
        $scope.addresss = Address.query();
        $scope.load = function(id) {
            Addresses.get({id : id}, function(result) {
                $scope.addresses = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:addressesUpdate', result);
            $modalInstance.close(result);
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
            $modalInstance.dismiss('cancel');
        };
}]);
