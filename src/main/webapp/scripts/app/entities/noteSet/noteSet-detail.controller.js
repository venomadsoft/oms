'use strict';

angular.module('omsApp')
    .controller('NoteSetDetailController', function ($scope, $rootScope, $stateParams, entity, NoteSet, Note) {
        $scope.noteSet = entity;
        $scope.load = function (id) {
            NoteSet.get({id: id}, function(result) {
                $scope.noteSet = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:noteSetUpdate', function(event, result) {
            $scope.noteSet = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
