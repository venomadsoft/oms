'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('derivedGsmShade', {
                parent: 'entity',
                url: '/derivedGsmShades',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DerivedGsmShades'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/derivedGsmShade/derivedGsmShades.html',
                        controller: 'DerivedGsmShadeController'
                    }
                },
                resolve: {
                }
            })
            .state('derivedGsmShade.detail', {
                parent: 'entity',
                url: '/derivedGsmShade/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DerivedGsmShade'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/derivedGsmShade/derivedGsmShade-detail.html',
                        controller: 'DerivedGsmShadeDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'DerivedGsmShade', function($stateParams, DerivedGsmShade) {
                        return DerivedGsmShade.get({id : $stateParams.id});
                    }]
                }
            })
            .state('derivedGsmShade.new', {
                parent: 'derivedGsmShade',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/derivedGsmShade/derivedGsmShade-dialog.html',
                        controller: 'DerivedGsmShadeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    minGsm: null,
                                    maxGsm: null,
                                    shade: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('derivedGsmShade', null, { reload: true });
                    }, function() {
                        $state.go('derivedGsmShade');
                    })
                }]
            })
            .state('derivedGsmShade.edit', {
                parent: 'derivedGsmShade',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/derivedGsmShade/derivedGsmShade-dialog.html',
                        controller: 'DerivedGsmShadeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DerivedGsmShade', function(DerivedGsmShade) {
                                return DerivedGsmShade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('derivedGsmShade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('derivedGsmShade.delete', {
                parent: 'derivedGsmShade',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/derivedGsmShade/derivedGsmShade-delete-dialog.html',
                        controller: 'DerivedGsmShadeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DerivedGsmShade', function(DerivedGsmShade) {
                                return DerivedGsmShade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('derivedGsmShade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
