'use strict';

angular.module('omsApp')
    .controller('PriceListDetailController', function ($scope, $rootScope, $stateParams, entity, PriceList, Price, Tax, CustomerGroup, DerivedGsmShade, ngTableParams, $filter) {
        $scope.priceList = entity;

        function getUpperQuality(quality) {
            return quality.replace(/\s+/g, '').toUpperCase();
        }

        function sortPrices(prices) {
            var qualities = new Set();
            var millPrices = [];
            for(var i = 0; i < prices.length; i++) {
                var price = prices[i];
                qualities.add(getUpperQuality(price.quality.label));
                if(millPrices[price.mill.id] === undefined) {
                    millPrices[price.mill.id] = {"mill": price.mill.name};
                }
                if(millPrices[price.mill.id]["gsmShades"] === undefined) {
                    millPrices[price.mill.id]["gsmShades"] = [];
                }
                if(millPrices[price.mill.id]["gsmShades"][price.simpleGsmShade.id] === undefined) {
                    millPrices[price.mill.id]["gsmShades"][price.simpleGsmShade.id] = {"gsmShade": price.simpleGsmShade, "qualities": {}};
                }
                var qualityLabel = getUpperQuality(price.quality.label);
                millPrices[price.mill.id]["gsmShades"][price.simpleGsmShade.id]["qualities"][qualityLabel] = price.value;
            }

            var results = [];
            for(var millPrice in millPrices) {
                for(var gsmShade in millPrices[millPrice]["gsmShades"]) {
                    qualities.forEach(function (value, key, setObj) {
                        if(millPrices[millPrice]["gsmShades"][gsmShade]["qualities"][value] == undefined) {
                            millPrices[millPrice]["gsmShades"][gsmShade]["qualities"][value] = "";
                        }
                    });

                    var result = {};
                    result["mill"] = millPrices[millPrice]["mill"];
                    result["shade"] = millPrices[millPrice]["gsmShades"][gsmShade]["gsmShade"]["shade"];
                    result["minGsm"] = millPrices[millPrice]["gsmShades"][gsmShade]["gsmShade"]["minGsm"];
                    result["maxGsm"] = millPrices[millPrice]["gsmShades"][gsmShade]["gsmShade"]["maxGsm"];
                    result["qualities"] = millPrices[millPrice]["gsmShades"][gsmShade]["qualities"];
                    results.push(result);
                }
            }

            $scope.qColumns = [];
            $scope.qColumns.push({"title": 'Mill', "sortable": 'mill', "filter":{ 'mill': 'text' }});
            $scope.qColumns.push({"title": 'Gsm-Shade', "sortable": 'shade', "filter":{ 'shade': 'text' }});
            qualities.forEach(function (value, key, setObj) {
                $scope.qColumns.push({"title": value, "field": value});
            });

            return results;
        }

        $scope.priceList.$promise.then(function(priceList) {

            var millPrices = sortPrices(priceList.pricess);

            $scope.pricesTable = new ngTableParams({}, {
                getData: function ($defer, params) {
                    $scope.data = params.sorting() ? $filter('orderBy')(millPrices, params.orderBy()) : millPrices;
                    $scope.data = params.filter() ? $filter('filter')($scope.data, params.filter()) : $scope.data;
                    $defer.resolve($scope.data);
                },
                counts: []
            });
        });
        $scope.load = function (id) {
            PriceList.get({id: id}, function(result) {
                $scope.priceList = result;
            });
        };
        var unsubscribe = $rootScope.$on('omsApp:priceListUpdate', function(event, result) {
            $scope.priceList = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
