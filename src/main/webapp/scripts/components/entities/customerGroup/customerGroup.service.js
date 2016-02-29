'use strict';

angular.module('omsApp')
    .factory('CustomerGroup', function ($resource, DateUtils) {
        return $resource('api/customerGroups/:id', {}, {
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
