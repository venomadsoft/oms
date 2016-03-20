'use strict';

angular.module('omsApp').controller('QualityDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Quality', 'Mill', 'Price',
        function($scope, $stateParams, $uibModalInstance, entity, Quality, Mill, Price) {

        $scope.quality = entity;
        $scope.findMill = function() {
            Mill.query(function(result) {
                $scope.mills = result;
                var mill;
                for(mill in $scope.mills) {
                    if($scope.mills[mill].id == $stateParams['id']) {
                        $scope.quality.mill = $scope.mills[mill];
                    }
                }
            });
        }
        $scope.findMill();

        $scope.prices = Price.query();
        $scope.load = function(id) {
            Quality.get({id : id}, function(result) {
                $scope.quality = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('omsApp:qualityUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.quality.id != null) {
                Quality.update($scope.quality, onSaveSuccess, onSaveError);
            } else {
                Quality.save($scope.quality, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
