'use strict';

angular.module('omsApp')
    .factory('NoteSet', function ($resource, DateUtils) {
        return $resource('api/noteSets/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
