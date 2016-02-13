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
                url: '/quality/{id}',
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
                        return Quality.get({id : $stateParams.id});
                    }]
                }
            })
            .state('quality.new', {
                parent: 'quality',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
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
                        $state.go('mill.detail', {id: result.mill.id}, { reload: true });
                    }, function() {
                        $state.go('quality');
                    })
                }]
            })
            .state('quality.edit', {
                parent: 'quality',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quality/quality-dialog.html',
                        controller: 'QualityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Quality', function(Quality) {
                                return Quality.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mill.detail', {id: result.mill.id}, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('quality.delete', {
                parent: 'quality',
                url: '/{id}/delete/{millId}',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/quality/quality-delete-dialog.html',
                        controller: 'QualityDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Quality', function(Quality) {
                                return Quality.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('mill.detail', {id: $stateParams.millId});
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
