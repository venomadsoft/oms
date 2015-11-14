'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('taxType', {
                parent: 'entity',
                url: '/taxTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TaxTypes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/taxType/taxTypes.html',
                        controller: 'TaxTypeController'
                    }
                },
                resolve: {
                }
            })
            .state('taxType.detail', {
                parent: 'entity',
                url: '/taxType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TaxType'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/taxType/taxType-detail.html',
                        controller: 'TaxTypeDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'TaxType', function($stateParams, TaxType) {
                        return TaxType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('taxType.new', {
                parent: 'taxType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/taxType/taxType-dialog.html',
                        controller: 'TaxTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    label: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('taxType', null, { reload: true });
                    }, function() {
                        $state.go('taxType');
                    })
                }]
            })
            .state('taxType.edit', {
                parent: 'taxType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/taxType/taxType-dialog.html',
                        controller: 'TaxTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TaxType', function(TaxType) {
                                return TaxType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('taxType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('taxType.delete', {
                parent: 'taxType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/taxType/taxType-delete-dialog.html',
                        controller: 'TaxTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TaxType', function(TaxType) {
                                return TaxType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('taxType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
