'use strict';

angular.module('omsApp').controller('DerivedGsmShadeDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'DerivedGsmShade', 'Formulae', 'SimpleGsmShade', 'Mill', 'PriceList',
        function($scope, $stateParams, $modalInstance, $q, entity, DerivedGsmShade, Formulae, SimpleGsmShade, Mill, PriceList) {

        $scope.derivedGsmShade = entity;
        $scope.formulaes = Formulae.query({filter: 'derivedgsmshade-is-null'});
        $q.all([$scope.derivedGsmShade.$promise, $scope.formulaes.$promise]).then(function() {
            if (!$scope.derivedGsmShade.formulae.id) {
                return $q.reject();
            }
            return Formulae.get({id : $scope.derivedGsmShade.formulae.id}).$promise;
        }).then(function(formulae) {
            $scope.formulaes.push(formulae);
        });
        $scope.simplegsmshades = SimpleGsmShade.query({filter: 'derivedgsmshade-is-null'});
        $q.all([$scope.derivedGsmShade.$promise, $scope.simplegsmshades.$promise]).then(function() {
            if (!$scope.derivedGsmShade.simpleGsmShade.id) {
                return $q.reject();
            }
            return SimpleGsmShade.get({id : $scope.derivedGsmShade.simpleGsmShade.id}).$promise;
        }).then(function(simpleGsmShade) {
            $scope.simplegsmshades.push(simpleGsmShade);
        });
        $scope.mills = Mill.query();
        $scope.pricelists = PriceList.query();
        $scope.load = function(id) {
            DerivedGsmShade.get({id : id}, function(result) {
                $scope.derivedGsmShade = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:derivedGsmShadeUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.derivedGsmShade.id != null) {
                DerivedGsmShade.update($scope.derivedGsmShade, onSaveSuccess, onSaveError);
            } else {
                DerivedGsmShade.save($scope.derivedGsmShade, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
