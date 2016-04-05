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
                url: '/simpleGsmShade/{simpleGsmShadeId}/mill/{millId}/code/{millCode}',
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
                        return SimpleGsmShade.get({id : $stateParams.simpleGsmShadeId});
                    }]
                }
            })
            .state('simpleGsmShade.new', {
                parent: 'mill.detail',
                url: '/new/',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
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
                    	$state.go('^', {id: $stateParams.id}, {reload: true});
                    }, function() {
                    	$state.go('^', {id: $stateParams.id}, {reload: true});
                    })
                }]
            })
            .state('simpleGsmShade.edit', {
            	parent: 'mill.detail',
            	url: '/simpleGsmShade/{simpleGsmShadeId}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/simpleGsmShade/simpleGsmShade-dialog.html',
                        controller: 'SimpleGsmShadeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SimpleGsmShade', function(SimpleGsmShade) {
                                return SimpleGsmShade.get({id : $stateParams.simpleGsmShadeId});
                            }]
                        }
                    }).result.then(function(result) {
                    	$state.go('^', {id: $stateParams.id},{reload: true});
                    }, function() {
                    	$state.go('^', {id: $stateParams.id},{reload: true});
                    })
                }]
            })
            .state('simpleGsmShade.delete', {
                parent: 'mill.detail',
                url: '/simpleGsmShade/{simpleGsmShadeId}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/simpleGsmShade/simpleGsmShade-delete-dialog.html',
                        controller: 'SimpleGsmShadeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SimpleGsmShade', function(SimpleGsmShade) {
                                return SimpleGsmShade.get({id : $stateParams.simpleGsmShadeId}});
                            }]
                        }
                    }).result.then(function(result) {
                    	$state.go('^', {id: $stateParams.id},{reload: true});
                    }, function() {
                    	$state.go('^', {id: $stateParams.id},{reload: true});
                    })
                }]
            });
    });
