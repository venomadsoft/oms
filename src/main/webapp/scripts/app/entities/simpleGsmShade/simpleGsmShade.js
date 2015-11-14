'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('simpleGsmShade', {
                parent: 'entity',
                url: '/simpleGsmShades',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SimpleGsmShades'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/simpleGsmShade/simpleGsmShades.html',
                        controller: 'SimpleGsmShadeController'
                    }
                },
                resolve: {
                }
            })
            .state('simpleGsmShade.detail', {
                parent: 'entity',
                url: '/simpleGsmShade/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SimpleGsmShade'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/simpleGsmShade/simpleGsmShade-detail.html',
                        controller: 'SimpleGsmShadeDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'SimpleGsmShade', function($stateParams, SimpleGsmShade) {
                        return SimpleGsmShade.get({id : $stateParams.id});
                    }]
                }
            })
            .state('simpleGsmShade.new', {
                parent: 'simpleGsmShade',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/simpleGsmShade/simpleGsmShade-dialog.html',
                        controller: 'SimpleGsmShadeDialogController',
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
                        $state.go('simpleGsmShade', null, { reload: true });
                    }, function() {
                        $state.go('simpleGsmShade');
                    })
                }]
            })
            .state('simpleGsmShade.edit', {
                parent: 'simpleGsmShade',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/simpleGsmShade/simpleGsmShade-dialog.html',
                        controller: 'SimpleGsmShadeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SimpleGsmShade', function(SimpleGsmShade) {
                                return SimpleGsmShade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('simpleGsmShade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('simpleGsmShade.delete', {
                parent: 'simpleGsmShade',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/simpleGsmShade/simpleGsmShade-delete-dialog.html',
                        controller: 'SimpleGsmShadeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SimpleGsmShade', function(SimpleGsmShade) {
                                return SimpleGsmShade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('simpleGsmShade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
