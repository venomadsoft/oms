'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('noteType', {
                parent: 'entity',
                url: '/noteTypes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'NoteTypes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/noteType/noteTypes.html',
                        controller: 'NoteTypeController'
                    }
                },
                resolve: {
                }
            })
            .state('noteType.detail', {
                parent: 'entity',
                url: '/noteType/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'NoteType'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/noteType/noteType-detail.html',
                        controller: 'NoteTypeDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'NoteType', function($stateParams, NoteType) {
                        return NoteType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('noteType.new', {
                parent: 'noteType',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/noteType/noteType-dialog.html',
                        controller: 'NoteTypeDialogController',
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
                        $state.go('noteType', null, { reload: true });
                    }, function() {
                        $state.go('noteType');
                    })
                }]
            })
            .state('noteType.edit', {
                parent: 'noteType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/noteType/noteType-dialog.html',
                        controller: 'NoteTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['NoteType', function(NoteType) {
                                return NoteType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('noteType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('noteType.delete', {
                parent: 'noteType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/noteType/noteType-delete-dialog.html',
                        controller: 'NoteTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['NoteType', function(NoteType) {
                                return NoteType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('noteType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
