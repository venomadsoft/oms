'use strict';

angular.module('omsApp')
    .controller('NoteSetController', function ($scope, $state, $modal, NoteSet) {
      
        $scope.noteSets = [];
        $scope.loadAll = function() {
            NoteSet.query(function(result) {
               $scope.noteSets = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.noteSet = {
                id: null
            };
        };
    });
