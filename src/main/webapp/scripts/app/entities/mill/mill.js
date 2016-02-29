'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mill', {
                parent: 'entity',
                url: '/mills',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Mills'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mill/mills.html',
                        controller: 'MillController'
                    }
                },
                resolve: {
                }
            })
            .state('mill.detail', {
                parent: 'entity',
                url: '/mill/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Mill'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/mill/mill-detail.html',
                        controller: 'MillDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Mill', function($stateParams, Mill) {
                        return Mill.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mill.new', {
                parent: 'mill',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/mill/mill-dialog.html',
                        controller: 'MillDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('mill', null, { reload: true });
                    }, function() {
                        $state.go('mill');
                    })
                }]
            })
            .state('mill.edit', {
                parent: 'mill',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/mill/mill-dialog.html',
                        controller: 'MillDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Mill', function(Mill) {
                                return Mill.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('mill.delete', {
                parent: 'mill',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/mill/mill-delete-dialog.html',
                        controller: 'MillDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Mill', function(Mill) {
                                return Mill.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mill', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
