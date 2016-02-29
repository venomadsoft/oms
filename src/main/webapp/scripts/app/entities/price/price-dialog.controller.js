'use strict';

angular.module('omsApp').controller('PriceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Price', 'Quality', 'SimpleGsmShade', 'Mill', 'PriceList',
        function($scope, $stateParams, $uibModalInstance, entity, Price, Quality, SimpleGsmShade, Mill, PriceList) {

        $scope.price = entity;
        $scope.qualitys = Quality.query();
        $scope.simplegsmshades = SimpleGsmShade.query();
        $scope.mills = Mill.query();
        $scope.pricelists = PriceList.query();
        $scope.load = function(id) {
            Price.get({id : id}, function(result) {
                $scope.price = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:priceUpdate', result);
            $uibModalInstance.close(result);
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
            $uibModalInstance.dismiss('cancel');
        };
}]);
