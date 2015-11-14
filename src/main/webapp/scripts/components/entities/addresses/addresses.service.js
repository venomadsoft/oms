'use strict';

angular.module('omsApp')
    .factory('Addresses', function ($resource, DateUtils) {
        return $resource('api/addressess/:id', {}, {
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
