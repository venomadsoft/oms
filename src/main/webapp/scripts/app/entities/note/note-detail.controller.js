'use strict';

angular.module('omsApp')
    .controller('NoteDetailController', function ($scope, $rootScope, $stateParams, entity, Note, NoteType, Line, NoteSet) {
        $scope.note = entity;
        $scope.load = function (id) {
            Note.get({id: id}, function(result) {
                $scope.note = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:noteUpdate', function(event, result) {
            $scope.note = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
