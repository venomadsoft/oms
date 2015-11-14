'use strict';

angular.module('omsApp').controller('PriceDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Price', 'Quality', 'SimpleGsmShade', 'Mill', 'PriceList',
        function($scope, $stateParams, $modalInstance, $q, entity, Price, Quality, SimpleGsmShade, Mill, PriceList) {

        $scope.price = entity;
        $scope.qualitys = Quality.query({filter: 'price-is-null'});
        $q.all([$scope.price.$promise, $scope.qualitys.$promise]).then(function() {
            if (!$scope.price.quality.id) {
                return $q.reject();
            }
            return Quality.get({id : $scope.price.quality.id}).$promise;
        }).then(function(quality) {
            $scope.qualitys.push(quality);
        });
        $scope.simplegsmshades = SimpleGsmShade.query({filter: 'price-is-null'});
        $q.all([$scope.price.$promise, $scope.simplegsmshades.$promise]).then(function() {
            if (!$scope.price.simpleGsmShade.id) {
                return $q.reject();
            }
            return SimpleGsmShade.get({id : $scope.price.simpleGsmShade.id}).$promise;
        }).then(function(simpleGsmShade) {
            $scope.simplegsmshades.push(simpleGsmShade);
        });
        $scope.mills = Mill.query();
        $scope.pricelists = PriceList.query();
        $scope.load = function(id) {
            Price.get({id : id}, function(result) {
                $scope.price = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:priceUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.price.id != null) {
                Price.update($scope.price, onSaveSuccess, onSaveError);
            } else {
                Price.save($scope.price, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
