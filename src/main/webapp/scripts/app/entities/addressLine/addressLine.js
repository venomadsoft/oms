'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('addressLine', {
                parent: 'entity',
                url: '/addressLines',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'AddressLines'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/addressLine/addressLines.html',
                        controller: 'AddressLineController'
                    }
                },
                resolve: {
                }
            })
            .state('addressLine.detail', {
                parent: 'entity',
                url: '/addressLine/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'AddressLine'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/addressLine/addressLine-detail.html',
                        controller: 'AddressLineDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'AddressLine', function($stateParams, AddressLine) {
                        return AddressLine.get({id : $stateParams.id});
                    }]
                }
            })
            .state('addressLine.new', {
                parent: 'addressLine',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/addressLine/addressLine-dialog.html',
                        controller: 'AddressLineDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    text: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('addressLine', null, { reload: true });
                    }, function() {
                        $state.go('addressLine');
                    })
                }]
            })
            .state('addressLine.edit', {
                parent: 'addressLine',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/addressLine/addressLine-dialog.html',
                        controller: 'AddressLineDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AddressLine', function(AddressLine) {
                                return AddressLine.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('addressLine', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('addressLine.delete', {
                parent: 'addressLine',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/addressLine/addressLine-delete-dialog.html',
                        controller: 'AddressLineDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AddressLine', function(AddressLine) {
                                return AddressLine.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('addressLine', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
