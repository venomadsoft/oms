'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('formulae', {
                parent: 'entity',
                url: '/formulaes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Formulaes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/formulae/formulaes.html',
                        controller: 'FormulaeController'
                    }
                },
                resolve: {
                }
            })
            .state('formulae.detail', {
                parent: 'entity',
                url: '/formulae/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Formulae'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/formulae/formulae-detail.html',
                        controller: 'FormulaeDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Formulae', function($stateParams, Formulae) {
                        return Formulae.get({id : $stateParams.id});
                    }]
                }
            })
            .state('formulae.new', {
                parent: 'formulae',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/formulae/formulae-dialog.html',
                        controller: 'FormulaeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('formulae', null, { reload: true });
                    }, function() {
                        $state.go('formulae');
                    })
                }]
            })
            .state('formulae.edit', {
                parent: 'formulae',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/formulae/formulae-dialog.html',
                        controller: 'FormulaeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Formulae', function(Formulae) {
                                return Formulae.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('formulae', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('formulae.delete', {
                parent: 'formulae',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/formulae/formulae-delete-dialog.html',
                        controller: 'FormulaeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Formulae', function(Formulae) {
                                return Formulae.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('formulae', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
