'use strict';

angular.module('omsApp')
    .controller('MillDetailController', function ($scope, $rootScope, $stateParams, entity, Mill, Addresses, NoteSet, Price, Quality, SimpleGsmShade, CustomerGroup, DerivedGsmShade) {
        $scope.mill = entity;
        $scope.load = function (id) {
            Mill.get({id: id}, function(result) {
                $scope.mill = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:millUpdate', function(event, result) {
            $scope.mill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
