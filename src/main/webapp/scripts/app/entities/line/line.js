'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('line', {
                parent: 'entity',
                url: '/lines',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Lines'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/line/lines.html',
                        controller: 'LineController'
                    }
                },
                resolve: {
                }
            })
            .state('line.detail', {
                parent: 'entity',
                url: '/line/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Line'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/line/line-detail.html',
                        controller: 'LineDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Line', function($stateParams, Line) {
                        return Line.get({id : $stateParams.id});
                    }]
                }
            })
            .state('line.new', {
                parent: 'line',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/line/line-dialog.html',
                        controller: 'LineDialogController',
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
                        $state.go('line', null, { reload: true });
                    }, function() {
                        $state.go('line');
                    })
                }]
            })
            .state('line.edit', {
                parent: 'line',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/line/line-dialog.html',
                        controller: 'LineDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Line', function(Line) {
                                return Line.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('line', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('line.delete', {
                parent: 'line',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/line/line-delete-dialog.html',
                        controller: 'LineDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Line', function(Line) {
                                return Line.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('line', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
