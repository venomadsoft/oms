'use strict';

angular.module('omsApp').controller('PriceListDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PriceList', 'Price', 'Tax', 'CustomerGroup', 'DerivedGsmShade',
        function($scope, $stateParams, $modalInstance, entity, PriceList, Price, Tax, CustomerGroup, DerivedGsmShade) {

        $scope.priceList = entity;
        $scope.prices = Price.query();
        $scope.taxs = Tax.query();
        $scope.customergroups = CustomerGroup.query();
        $scope.derivedgsmshades = DerivedGsmShade.query();
        $scope.load = function(id) {
            PriceList.get({id : id}, function(result) {
                $scope.priceList = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:priceListUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.priceList.id != null) {
                PriceList.update($scope.priceList, onSaveSuccess, onSaveError);
            } else {
                PriceList.save($scope.priceList, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
