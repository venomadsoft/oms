'use strict';

angular.module('omsApp').controller('CustomerGroupDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'CustomerGroup', 'PriceList', 'Mill', 'Customer', 'NoteSet', 'Formulae',
        function($scope, $stateParams, $modalInstance, $q, entity, CustomerGroup, PriceList, Mill, Customer, NoteSet, Formulae) {

        $scope.customerGroup = entity;
        $scope.pricelists = PriceList.query();
        $scope.mills = Mill.query();
        $scope.customers = Customer.query();
        $scope.notess = NoteSet.query({filter: 'customergroup-is-null'});
        $q.all([$scope.customerGroup.$promise, $scope.notess.$promise]).then(function() {
            if (!$scope.customerGroup.notes.id) {
                return $q.reject();
            }
            return NoteSet.get({id : $scope.customerGroup.notes.id}).$promise;
        }).then(function(notes) {
            $scope.notess.push(notes);
        });
        $scope.formulaes = Formulae.query({filter: 'customergroup-is-null'});
        $q.all([$scope.customerGroup.$promise, $scope.formulaes.$promise]).then(function() {
            if (!$scope.customerGroup.formulae.id) {
                return $q.reject();
            }
            return Formulae.get({id : $scope.customerGroup.formulae.id}).$promise;
        }).then(function(formulae) {
            $scope.formulaes.push(formulae);
        });
        $scope.load = function(id) {
            CustomerGroup.get({id : id}, function(result) {
                $scope.customerGroup = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:customerGroupUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.customerGroup.id != null) {
                CustomerGroup.update($scope.customerGroup, onSaveSuccess, onSaveError);
            } else {
                CustomerGroup.save($scope.customerGroup, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
