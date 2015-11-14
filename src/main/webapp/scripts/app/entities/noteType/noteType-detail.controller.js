'use strict';

angular.module('omsApp')
    .controller('NoteTypeDetailController', function ($scope, $rootScope, $stateParams, entity, NoteType) {
        $scope.noteType = entity;
        $scope.load = function (id) {
            NoteType.get({id: id}, function(result) {
                $scope.noteType = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:noteTypeUpdate', function(event, result) {
            $scope.noteType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
