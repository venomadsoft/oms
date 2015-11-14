'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('addresses', {
                parent: 'entity',
                url: '/addressess',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Addressess'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/addresses/addressess.html',
                        controller: 'AddressesController'
                    }
                },
                resolve: {
                }
            })
            .state('addresses.detail', {
                parent: 'entity',
                url: '/addresses/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Addresses'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/addresses/addresses-detail.html',
                        controller: 'AddressesDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Addresses', function($stateParams, Addresses) {
                        return Addresses.get({id : $stateParams.id});
                    }]
                }
            })
            .state('addresses.new', {
                parent: 'addresses',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/addresses/addresses-dialog.html',
                        controller: 'AddressesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('addresses', null, { reload: true });
                    }, function() {
                        $state.go('addresses');
                    })
                }]
            })
            .state('addresses.edit', {
                parent: 'addresses',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/addresses/addresses-dialog.html',
                        controller: 'AddressesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Addresses', function(Addresses) {
                                return Addresses.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('addresses', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('addresses.delete', {
                parent: 'addresses',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/addresses/addresses-delete-dialog.html',
                        controller: 'AddressesDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Addresses', function(Addresses) {
                                return Addresses.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('addresses', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
