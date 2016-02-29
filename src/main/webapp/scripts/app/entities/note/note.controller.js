'use strict';

angular.module('omsApp')
    .controller('NoteController', function ($scope, $state, Note) {

        $scope.notes = [];
        $scope.loadAll = function() {
            Note.query(function(result) {
               $scope.notes = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.note = {
                id: null
            };
        };
    });
