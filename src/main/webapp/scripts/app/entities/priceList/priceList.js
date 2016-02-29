'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('priceList', {
                parent: 'entity',
                url: '/priceLists',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PriceLists'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/priceList/priceLists.html',
                        controller: 'PriceListController'
                    }
                },
                resolve: {
                }
            })
            .state('priceList.detail', {
                parent: 'entity',
                url: '/priceList/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PriceList'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/priceList/priceList-detail.html',
                        controller: 'PriceListDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PriceList', function($stateParams, PriceList) {
                        return PriceList.get({id : $stateParams.id});
                    }]
                }
            })
            .state('priceList.new', {
                parent: 'priceList',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/priceList/priceList-dialog.html',
                        controller: 'PriceListDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    wefDateFrom: null,
                                    wefDateTo: null,
                                    active: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('priceList', null, { reload: true });
                    }, function() {
                        $state.go('priceList');
                    })
                }]
            })
            .state('priceList.edit', {
                parent: 'priceList',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/priceList/priceList-dialog.html',
                        controller: 'PriceListDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PriceList', function(PriceList) {
                                return PriceList.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('priceList', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('priceList.delete', {
                parent: 'priceList',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/priceList/priceList-delete-dialog.html',
                        controller: 'PriceListDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PriceList', function(PriceList) {
                                return PriceList.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('priceList', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
