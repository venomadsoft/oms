'use strict';

angular.module('omsApp').controller('MillDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Mill', 'Addresses', 'NoteSet', 'Price', 'Quality', 'SimpleGsmShade', 'CustomerGroup', 'DerivedGsmShade',
        function($scope, $stateParams, $modalInstance, $q, entity, Mill, Addresses, NoteSet, Price, Quality, SimpleGsmShade, CustomerGroup, DerivedGsmShade) {

        $scope.mill = entity;
        $scope.addressess = Addresses.query({filter: 'mill-is-null'});
        $q.all([$scope.mill.$promise, $scope.addressess.$promise]).then(function() {
            if (!$scope.mill.addresses.id) {
                return $q.reject();
            }
            return Addresses.get({id : $scope.mill.addresses.id}).$promise;
        }).then(function(addresses) {
            $scope.addressess.push(addresses);
        });
        $scope.notess = NoteSet.query({filter: 'mill-is-null'});
        $q.all([$scope.mill.$promise, $scope.notess.$promise]).then(function() {
            if (!$scope.mill.notes.id) {
                return $q.reject();
            }
            return NoteSet.get({id : $scope.mill.notes.id}).$promise;
        }).then(function(notes) {
            $scope.notess.push(notes);
        });
        $scope.prices = Price.query();
        $scope.qualitys = Quality.query();
        $scope.simplegsmshades = SimpleGsmShade.query();
        $scope.customergroups = CustomerGroup.query();
        $scope.derivedgsmshades = DerivedGsmShade.query();
        $scope.load = function(id) {
            Mill.get({id : id}, function(result) {
                $scope.mill = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:millUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.mill.id != null) {
                Mill.update($scope.mill, onSaveSuccess, onSaveError);
            } else {
                Mill.save($scope.mill, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
