(window["webpackJsonp"] = window["webpackJsonp"] || []).push([[22],{

/***/ "./src/main/webapp/app/admin/admin-routing.module.ts":
/*!***********************************************************!*\
  !*** ./src/main/webapp/app/admin/admin-routing.module.ts ***!
  \***********************************************************/
/*! exports provided: AdminRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, \"AdminRoutingModule\", function() { return AdminRoutingModule; });\n/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ \"./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js\");\n/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ \"./node_modules/@angular/router/__ivy_ngcc__/fesm2015/router.js\");\n\n\n\n\n/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */\nclass AdminRoutingModule {\n}\nAdminRoutingModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵdefineNgModule\"]({ type: AdminRoutingModule });\nAdminRoutingModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵdefineInjector\"]({ factory: function AdminRoutingModule_Factory(t) { return new (t || AdminRoutingModule)(); }, imports: [[\n            /* jhipster-needle-add-admin-module - JHipster will add admin modules here */\n            _angular_router__WEBPACK_IMPORTED_MODULE_1__[\"RouterModule\"].forChild([\n                {\n                    path: 'user-management',\n                    loadChildren: () => __webpack_require__.e(/*! import() */ 11).then(__webpack_require__.bind(null, /*! ./user-management/user-management.module */ \"./src/main/webapp/app/admin/user-management/user-management.module.ts\")).then(m => m.UserManagementModule),\n                    data: {\n                        pageTitle: 'Users'\n                    }\n                },\n                {\n                    path: 'audits',\n                    loadChildren: () => __webpack_require__.e(/*! import() */ 16).then(__webpack_require__.bind(null, /*! ./audits/audits.module */ \"./src/main/webapp/app/admin/audits/audits.module.ts\")).then(m => m.AuditsModule)\n                },\n                {\n                    path: 'configuration',\n                    loadChildren: () => __webpack_require__.e(/*! import() */ 19).then(__webpack_require__.bind(null, /*! ./configuration/configuration.module */ \"./src/main/webapp/app/admin/configuration/configuration.module.ts\")).then(m => m.ConfigurationModule)\n                },\n                {\n                    path: 'docs',\n                    loadChildren: () => __webpack_require__.e(/*! import() */ 21).then(__webpack_require__.bind(null, /*! ./docs/docs.module */ \"./src/main/webapp/app/admin/docs/docs.module.ts\")).then(m => m.DocsModule)\n                },\n                {\n                    path: 'health',\n                    loadChildren: () => __webpack_require__.e(/*! import() */ 17).then(__webpack_require__.bind(null, /*! ./health/health.module */ \"./src/main/webapp/app/admin/health/health.module.ts\")).then(m => m.HealthModule)\n                },\n                {\n                    path: 'logs',\n                    loadChildren: () => __webpack_require__.e(/*! import() */ 18).then(__webpack_require__.bind(null, /*! ./logs/logs.module */ \"./src/main/webapp/app/admin/logs/logs.module.ts\")).then(m => m.LogsModule)\n                },\n                {\n                    path: 'metrics',\n                    loadChildren: () => __webpack_require__.e(/*! import() */ 20).then(__webpack_require__.bind(null, /*! ./metrics/metrics.module */ \"./src/main/webapp/app/admin/metrics/metrics.module.ts\")).then(m => m.MetricsModule)\n                }\n                /* jhipster-needle-add-admin-route - JHipster will add admin routes here */\n            ])\n        ]] });\n(function () { (typeof ngJitMode === \"undefined\" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵsetNgModuleScope\"](AdminRoutingModule, { imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__[\"RouterModule\"]] }); })();\n/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵsetClassMetadata\"](AdminRoutingModule, [{\n        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"NgModule\"],\n        args: [{\n                imports: [\n                    /* jhipster-needle-add-admin-module - JHipster will add admin modules here */\n                    _angular_router__WEBPACK_IMPORTED_MODULE_1__[\"RouterModule\"].forChild([\n                        {\n                            path: 'user-management',\n                            loadChildren: () => __webpack_require__.e(/*! import() */ 11).then(__webpack_require__.bind(null, /*! ./user-management/user-management.module */ \"./src/main/webapp/app/admin/user-management/user-management.module.ts\")).then(m => m.UserManagementModule),\n                            data: {\n                                pageTitle: 'Users'\n                            }\n                        },\n                        {\n                            path: 'audits',\n                            loadChildren: () => __webpack_require__.e(/*! import() */ 16).then(__webpack_require__.bind(null, /*! ./audits/audits.module */ \"./src/main/webapp/app/admin/audits/audits.module.ts\")).then(m => m.AuditsModule)\n                        },\n                        {\n                            path: 'configuration',\n                            loadChildren: () => __webpack_require__.e(/*! import() */ 19).then(__webpack_require__.bind(null, /*! ./configuration/configuration.module */ \"./src/main/webapp/app/admin/configuration/configuration.module.ts\")).then(m => m.ConfigurationModule)\n                        },\n                        {\n                            path: 'docs',\n                            loadChildren: () => __webpack_require__.e(/*! import() */ 21).then(__webpack_require__.bind(null, /*! ./docs/docs.module */ \"./src/main/webapp/app/admin/docs/docs.module.ts\")).then(m => m.DocsModule)\n                        },\n                        {\n                            path: 'health',\n                            loadChildren: () => __webpack_require__.e(/*! import() */ 17).then(__webpack_require__.bind(null, /*! ./health/health.module */ \"./src/main/webapp/app/admin/health/health.module.ts\")).then(m => m.HealthModule)\n                        },\n                        {\n                            path: 'logs',\n                            loadChildren: () => __webpack_require__.e(/*! import() */ 18).then(__webpack_require__.bind(null, /*! ./logs/logs.module */ \"./src/main/webapp/app/admin/logs/logs.module.ts\")).then(m => m.LogsModule)\n                        },\n                        {\n                            path: 'metrics',\n                            loadChildren: () => __webpack_require__.e(/*! import() */ 20).then(__webpack_require__.bind(null, /*! ./metrics/metrics.module */ \"./src/main/webapp/app/admin/metrics/metrics.module.ts\")).then(m => m.MetricsModule)\n                        }\n                        /* jhipster-needle-add-admin-route - JHipster will add admin routes here */\n                    ])\n                ]\n            }]\n    }], null, null); })();\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8vLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL2FkbWluLXJvdXRpbmcubW9kdWxlLnRzPzFlZjgiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7QUFBQTtBQUFBO0FBQUE7QUFBeUM7QUFDTTs7O0FBQy9DLDRGQUE0RjtBQXlDckYsTUFBTSxrQkFBa0I7O2lHQUFsQixrQkFBa0I7OEpBQWxCLGtCQUFrQixrQkF0Q3BCO1lBQ1AsNkVBQTZFO1lBQzdFLDREQUFZLENBQUMsUUFBUSxDQUFDO2dCQUNwQjtvQkFDRSxJQUFJLEVBQUUsaUJBQWlCO29CQUN2QixZQUFZLEVBQUUsR0FBRyxFQUFFLENBQUMsdU1BQWtELENBQUMsSUFBSSxDQUFDLENBQUMsQ0FBQyxFQUFFLENBQUMsQ0FBQyxDQUFDLG9CQUFvQixDQUFDO29CQUN4RyxJQUFJLEVBQUU7d0JBQ0osU0FBUyxFQUFFLE9BQU87cUJBQ25CO2lCQUNGO2dCQUNEO29CQUNFLElBQUksRUFBRSxRQUFRO29CQUNkLFlBQVksRUFBRSxHQUFHLEVBQUUsQ0FBQyxtS0FBZ0MsQ0FBQyxJQUFJLENBQUMsQ0FBQyxDQUFDLEVBQUUsQ0FBQyxDQUFDLENBQUMsWUFBWSxDQUFDO2lCQUMvRTtnQkFDRDtvQkFDRSxJQUFJLEVBQUUsZUFBZTtvQkFDckIsWUFBWSxFQUFFLEdBQUcsRUFBRSxDQUFDLCtMQUE4QyxDQUFDLElBQUksQ0FBQyxDQUFDLENBQUMsRUFBRSxDQUFDLENBQUMsQ0FBQyxtQkFBbUIsQ0FBQztpQkFDcEc7Z0JBQ0Q7b0JBQ0UsSUFBSSxFQUFFLE1BQU07b0JBQ1osWUFBWSxFQUFFLEdBQUcsRUFBRSxDQUFDLDJKQUE0QixDQUFDLElBQUksQ0FBQyxDQUFDLENBQUMsRUFBRSxDQUFDLENBQUMsQ0FBQyxVQUFVLENBQUM7aUJBQ3pFO2dCQUNEO29CQUNFLElBQUksRUFBRSxRQUFRO29CQUNkLFlBQVksRUFBRSxHQUFHLEVBQUUsQ0FBQyxtS0FBZ0MsQ0FBQyxJQUFJLENBQUMsQ0FBQyxDQUFDLEVBQUUsQ0FBQyxDQUFDLENBQUMsWUFBWSxDQUFDO2lCQUMvRTtnQkFDRDtvQkFDRSxJQUFJLEVBQUUsTUFBTTtvQkFDWixZQUFZLEVBQUUsR0FBRyxFQUFFLENBQUMsMkpBQTRCLENBQUMsSUFBSSxDQUFDLENBQUMsQ0FBQyxFQUFFLENBQUMsQ0FBQyxDQUFDLFVBQVUsQ0FBQztpQkFDekU7Z0JBQ0Q7b0JBQ0UsSUFBSSxFQUFFLFNBQVM7b0JBQ2YsWUFBWSxFQUFFLEdBQUcsRUFBRSxDQUFDLHVLQUFrQyxDQUFDLElBQUksQ0FBQyxDQUFDLENBQUMsRUFBRSxDQUFDLENBQUMsQ0FBQyxhQUFhLENBQUM7aUJBQ2xGO2dCQUNELDJFQUEyRTthQUM1RSxDQUFDO1NBQ0g7bUlBRVUsa0JBQWtCOzZGQUFsQixrQkFBa0I7Y0F2QzlCLHNEQUFRO2VBQUM7Z0JBQ1IsT0FBTyxFQUFFO29CQUNQLDZFQUE2RTtvQkFDN0UsNERBQVksQ0FBQyxRQUFRLENBQUM7d0JBQ3BCOzRCQUNFLElBQUksRUFBRSxpQkFBaUI7NEJBQ3ZCLFlBQVksRUFBRSxHQUFHLEVBQUUsQ0FBQyx1TUFBa0QsQ0FBQyxJQUFJLENBQUMsQ0FBQyxDQUFDLEVBQUUsQ0FBQyxDQUFDLENBQUMsb0JBQW9CLENBQUM7NEJBQ3hHLElBQUksRUFBRTtnQ0FDSixTQUFTLEVBQUUsT0FBTzs2QkFDbkI7eUJBQ0Y7d0JBQ0Q7NEJBQ0UsSUFBSSxFQUFFLFFBQVE7NEJBQ2QsWUFBWSxFQUFFLEdBQUcsRUFBRSxDQUFDLG1LQUFnQyxDQUFDLElBQUksQ0FBQyxDQUFDLENBQUMsRUFBRSxDQUFDLENBQUMsQ0FBQyxZQUFZLENBQUM7eUJBQy9FO3dCQUNEOzRCQUNFLElBQUksRUFBRSxlQUFlOzRCQUNyQixZQUFZLEVBQUUsR0FBRyxFQUFFLENBQUMsK0xBQThDLENBQUMsSUFBSSxDQUFDLENBQUMsQ0FBQyxFQUFFLENBQUMsQ0FBQyxDQUFDLG1CQUFtQixDQUFDO3lCQUNwRzt3QkFDRDs0QkFDRSxJQUFJLEVBQUUsTUFBTTs0QkFDWixZQUFZLEVBQUUsR0FBRyxFQUFFLENBQUMsMkpBQTRCLENBQUMsSUFBSSxDQUFDLENBQUMsQ0FBQyxFQUFFLENBQUMsQ0FBQyxDQUFDLFVBQVUsQ0FBQzt5QkFDekU7d0JBQ0Q7NEJBQ0UsSUFBSSxFQUFFLFFBQVE7NEJBQ2QsWUFBWSxFQUFFLEdBQUcsRUFBRSxDQUFDLG1LQUFnQyxDQUFDLElBQUksQ0FBQyxDQUFDLENBQUMsRUFBRSxDQUFDLENBQUMsQ0FBQyxZQUFZLENBQUM7eUJBQy9FO3dCQUNEOzRCQUNFLElBQUksRUFBRSxNQUFNOzRCQUNaLFlBQVksRUFBRSxHQUFHLEVBQUUsQ0FBQywySkFBNEIsQ0FBQyxJQUFJLENBQUMsQ0FBQyxDQUFDLEVBQUUsQ0FBQyxDQUFDLENBQUMsVUFBVSxDQUFDO3lCQUN6RTt3QkFDRDs0QkFDRSxJQUFJLEVBQUUsU0FBUzs0QkFDZixZQUFZLEVBQUUsR0FBRyxFQUFFLENBQUMsdUtBQWtDLENBQUMsSUFBSSxDQUFDLENBQUMsQ0FBQyxFQUFFLENBQUMsQ0FBQyxDQUFDLGFBQWEsQ0FBQzt5QkFDbEY7d0JBQ0QsMkVBQTJFO3FCQUM1RSxDQUFDO2lCQUNIO2FBQ0YiLCJmaWxlIjoiLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL2FkbWluLXJvdXRpbmcubW9kdWxlLnRzLmpzIiwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHsgTmdNb2R1bGUgfSBmcm9tICdAYW5ndWxhci9jb3JlJztcbmltcG9ydCB7IFJvdXRlck1vZHVsZSB9IGZyb20gJ0Bhbmd1bGFyL3JvdXRlcic7XG4vKiBqaGlwc3Rlci1uZWVkbGUtYWRkLWFkbWluLW1vZHVsZS1pbXBvcnQgLSBKSGlwc3RlciB3aWxsIGFkZCBhZG1pbiBtb2R1bGVzIGltcG9ydHMgaGVyZSAqL1xuXG5ATmdNb2R1bGUoe1xuICBpbXBvcnRzOiBbXG4gICAgLyogamhpcHN0ZXItbmVlZGxlLWFkZC1hZG1pbi1tb2R1bGUgLSBKSGlwc3RlciB3aWxsIGFkZCBhZG1pbiBtb2R1bGVzIGhlcmUgKi9cbiAgICBSb3V0ZXJNb2R1bGUuZm9yQ2hpbGQoW1xuICAgICAge1xuICAgICAgICBwYXRoOiAndXNlci1tYW5hZ2VtZW50JyxcbiAgICAgICAgbG9hZENoaWxkcmVuOiAoKSA9PiBpbXBvcnQoJy4vdXNlci1tYW5hZ2VtZW50L3VzZXItbWFuYWdlbWVudC5tb2R1bGUnKS50aGVuKG0gPT4gbS5Vc2VyTWFuYWdlbWVudE1vZHVsZSksXG4gICAgICAgIGRhdGE6IHtcbiAgICAgICAgICBwYWdlVGl0bGU6ICdVc2VycydcbiAgICAgICAgfVxuICAgICAgfSxcbiAgICAgIHtcbiAgICAgICAgcGF0aDogJ2F1ZGl0cycsXG4gICAgICAgIGxvYWRDaGlsZHJlbjogKCkgPT4gaW1wb3J0KCcuL2F1ZGl0cy9hdWRpdHMubW9kdWxlJykudGhlbihtID0+IG0uQXVkaXRzTW9kdWxlKVxuICAgICAgfSxcbiAgICAgIHtcbiAgICAgICAgcGF0aDogJ2NvbmZpZ3VyYXRpb24nLFxuICAgICAgICBsb2FkQ2hpbGRyZW46ICgpID0+IGltcG9ydCgnLi9jb25maWd1cmF0aW9uL2NvbmZpZ3VyYXRpb24ubW9kdWxlJykudGhlbihtID0+IG0uQ29uZmlndXJhdGlvbk1vZHVsZSlcbiAgICAgIH0sXG4gICAgICB7XG4gICAgICAgIHBhdGg6ICdkb2NzJyxcbiAgICAgICAgbG9hZENoaWxkcmVuOiAoKSA9PiBpbXBvcnQoJy4vZG9jcy9kb2NzLm1vZHVsZScpLnRoZW4obSA9PiBtLkRvY3NNb2R1bGUpXG4gICAgICB9LFxuICAgICAge1xuICAgICAgICBwYXRoOiAnaGVhbHRoJyxcbiAgICAgICAgbG9hZENoaWxkcmVuOiAoKSA9PiBpbXBvcnQoJy4vaGVhbHRoL2hlYWx0aC5tb2R1bGUnKS50aGVuKG0gPT4gbS5IZWFsdGhNb2R1bGUpXG4gICAgICB9LFxuICAgICAge1xuICAgICAgICBwYXRoOiAnbG9ncycsXG4gICAgICAgIGxvYWRDaGlsZHJlbjogKCkgPT4gaW1wb3J0KCcuL2xvZ3MvbG9ncy5tb2R1bGUnKS50aGVuKG0gPT4gbS5Mb2dzTW9kdWxlKVxuICAgICAgfSxcbiAgICAgIHtcbiAgICAgICAgcGF0aDogJ21ldHJpY3MnLFxuICAgICAgICBsb2FkQ2hpbGRyZW46ICgpID0+IGltcG9ydCgnLi9tZXRyaWNzL21ldHJpY3MubW9kdWxlJykudGhlbihtID0+IG0uTWV0cmljc01vZHVsZSlcbiAgICAgIH1cbiAgICAgIC8qIGpoaXBzdGVyLW5lZWRsZS1hZGQtYWRtaW4tcm91dGUgLSBKSGlwc3RlciB3aWxsIGFkZCBhZG1pbiByb3V0ZXMgaGVyZSAqL1xuICAgIF0pXG4gIF1cbn0pXG5leHBvcnQgY2xhc3MgQWRtaW5Sb3V0aW5nTW9kdWxlIHt9XG4iXSwic291cmNlUm9vdCI6IiJ9\n//# sourceURL=webpack-internal:///./src/main/webapp/app/admin/admin-routing.module.ts\n");

/***/ })

}]);