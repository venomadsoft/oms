'use strict';

angular.module('omsApp').controller('CustomerDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Customer', 'Addresses', 'NoteSet', 'Formulae', 'CustomerGroup',
        function($scope, $stateParams, $modalInstance, $q, entity, Customer, Addresses, NoteSet, Formulae, CustomerGroup) {

        $scope.customer = entity;
        $scope.addressess = Addresses.query({filter: 'customer-is-null'});
        $q.all([$scope.customer.$promise, $scope.addressess.$promise]).then(function() {
            if (!$scope.customer.addresses.id) {
                return $q.reject();
            }
            return Addresses.get({id : $scope.customer.addresses.id}).$promise;
        }).then(function(addresses) {
            $scope.addressess.push(addresses);
        });
        $scope.notess = NoteSet.query({filter: 'customer-is-null'});
        $q.all([$scope.customer.$promise, $scope.notess.$promise]).then(function() {
            if (!$scope.customer.notes.id) {
                return $q.reject();
            }
            return NoteSet.get({id : $scope.customer.notes.id}).$promise;
        }).then(function(notes) {
            $scope.notess.push(notes);
        });
        $scope.formulaes = Formulae.query({filter: 'customer-is-null'});
        $q.all([$scope.customer.$promise, $scope.formulaes.$promise]).then(function() {
            if (!$scope.customer.formulae.id) {
                return $q.reject();
            }
            return Formulae.get({id : $scope.customer.formulae.id}).$promise;
        }).then(function(formulae) {
            $scope.formulaes.push(formulae);
        });
        $scope.customergroups = CustomerGroup.query();
        $scope.load = function(id) {
            Customer.get({id : id}, function(result) {
                $scope.customer = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:customerUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.customer.id != null) {
                Customer.update($scope.customer, onSaveSuccess, onSaveError);
            } else {
                Customer.save($scope.customer, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
