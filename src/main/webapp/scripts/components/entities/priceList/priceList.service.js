'use strict';

angular.module('omsApp')
    .factory('PriceList', function ($resource, DateUtils) {
        return $resource('api/priceLists/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.wefDateFrom = DateUtils.convertLocaleDateFromServer(data.wefDateFrom);
                    data.wefDateTo = DateUtils.convertLocaleDateFromServer(data.wefDateTo);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.wefDateFrom = DateUtils.convertLocaleDateToServer(data.wefDateFrom);
                    data.wefDateTo = DateUtils.convertLocaleDateToServer(data.wefDateTo);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.wefDateFrom = DateUtils.convertLocaleDateToServer(data.wefDateFrom);
                    data.wefDateTo = DateUtils.convertLocaleDateToServer(data.wefDateTo);
                    return angular.toJson(data);
                }
            }
        });
    });
