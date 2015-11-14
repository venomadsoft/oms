'use strict';

angular.module('omsApp')
    .controller('NoteTypeController', function ($scope, $state, $modal, NoteType) {
      
        $scope.noteTypes = [];
        $scope.loadAll = function() {
            NoteType.query(function(result) {
               $scope.noteTypes = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.noteType = {
                label: null,
                id: null
            };
        };
    });
