'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('quality', {
                parent: 'entity',
                url: '/qualitys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Qualitys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/quality/qualitys.html',
                        controller: 'QualityController'
                    }
                },
                resolve: {
                }
            })
            .state('quality.detail', {
                parent: 'entity',
                url: '/quality/{qualityId}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Quality'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/quality/quality-detail.html',
                        controller: 'QualityDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Quality', function($stateParams, Quality) {
                        return Quality.get({id : $stateParams.qualityId});
                    }]
                }
            })
            .state('quality.new', {
                parent: 'mill.detail',
                url: '/new/',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/quality/quality-dialog.html',
                        controller: 'QualityDialogController',
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
                        $state.go('^', {id: $stateParams.id}, {reload: true});
                    }, function() {
                        $state.go('^', {id: $stateParams.id}, {reload: true});
                    })
                }]
            })
            .state('quality.edit', {
                parent: 'mill.detail',
                url: '/{qualityId}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/quality/quality-dialog.html',
                        controller: 'QualityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Quality', function(Quality) {
                                return Quality.get({id : $stateParams.qualityId});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('^', {id: $stateParams.id},{reload: true});
                    }, function() {
                        $state.go('^', {id: $stateParams.id},{reload: true});
                    })
                }]
            })
            .state('quality.delete', {
                parent: 'mill.detail',
                url: '/{qualityId}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/quality/quality-delete-dialog.html',
                        controller: 'QualityDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Quality', function(Quality) {
                                return Quality.get({id : $stateParams.qualityId});
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
