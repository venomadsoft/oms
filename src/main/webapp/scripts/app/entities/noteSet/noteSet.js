'use strict';

angular.module('omsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('noteSet', {
                parent: 'entity',
                url: '/noteSets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'NoteSets'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/noteSet/noteSets.html',
                        controller: 'NoteSetController'
                    }
                },
                resolve: {
                }
            })
            .state('noteSet.detail', {
                parent: 'entity',
                url: '/noteSet/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'NoteSet'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/noteSet/noteSet-detail.html',
                        controller: 'NoteSetDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'NoteSet', function($stateParams, NoteSet) {
                        return NoteSet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('noteSet.new', {
                parent: 'noteSet',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/noteSet/noteSet-dialog.html',
                        controller: 'NoteSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('noteSet', null, { reload: true });
                    }, function() {
                        $state.go('noteSet');
                    })
                }]
            })
            .state('noteSet.edit', {
                parent: 'noteSet',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/noteSet/noteSet-dialog.html',
                        controller: 'NoteSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['NoteSet', function(NoteSet) {
                                return NoteSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('noteSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('noteSet.delete', {
                parent: 'noteSet',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/noteSet/noteSet-delete-dialog.html',
                        controller: 'NoteSetDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['NoteSet', function(NoteSet) {
                                return NoteSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('noteSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
