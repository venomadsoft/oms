'use strict';

angular.module('omsApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


