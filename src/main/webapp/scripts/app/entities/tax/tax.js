'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tax', {
                parent: 'entity',
                url: '/taxs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Taxs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tax/taxs.html',
                        controller: 'TaxController'
                    }
                },
                resolve: {
                }
            })
            .state('tax.detail', {
                parent: 'entity',
                url: '/tax/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Tax'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tax/tax-detail.html',
                        controller: 'TaxDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Tax', function($stateParams, Tax) {
                        return Tax.get({id : $stateParams.id});
                    }]
                }
            })
            .state('tax.new', {
                parent: 'tax',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tax/tax-dialog.html',
                        controller: 'TaxDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    rate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tax', null, { reload: true });
                    }, function() {
                        $state.go('tax');
                    })
                }]
            })
            .state('tax.edit', {
                parent: 'tax',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tax/tax-dialog.html',
                        controller: 'TaxDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Tax', function(Tax) {
                                return Tax.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tax', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('tax.delete', {
                parent: 'tax',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tax/tax-delete-dialog.html',
                        controller: 'TaxDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Tax', function(Tax) {
                                return Tax.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tax', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
