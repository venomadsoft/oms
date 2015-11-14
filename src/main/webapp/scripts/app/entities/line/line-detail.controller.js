'use strict';

angular.module('omsApp')
    .controller('LineDetailController', function ($scope, $rootScope, $stateParams, entity, Line, Note) {
        $scope.line = entity;
        $scope.load = function (id) {
            Line.get({id: id}, function(result) {
                $scope.line = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:lineUpdate', function(event, result) {
            $scope.line = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
