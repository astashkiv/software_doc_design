(window["webpackJsonp"] = window["webpackJsonp"] || []).push([[21],{

/***/ "./src/main/webapp/app/admin/docs/docs.component.ts":
/*!**********************************************************!*\
  !*** ./src/main/webapp/app/admin/docs/docs.component.ts ***!
  \**********************************************************/
/*! exports provided: DocsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, \"DocsComponent\", function() { return DocsComponent; });\n/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ \"./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js\");\n\n\nclass DocsComponent {\n}\nDocsComponent.ɵfac = function DocsComponent_Factory(t) { return new (t || DocsComponent)(); };\nDocsComponent.ɵcmp = _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵdefineComponent\"]({ type: DocsComponent, selectors: [[\"jhi-docs\"]], decls: 2, vars: 0, consts: [[\"src\", \"swagger-ui/index.html\", \"width\", \"100%\", \"height\", \"900\", \"seamless\", \"\", \"target\", \"_top\", \"title\", \"Swagger UI\", 1, \"border-0\"]], template: function DocsComponent_Template(rf, ctx) { if (rf & 1) {\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelement\"](0, \"iframe\", 0);\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](1, \" \");\n    } }, styles: [\"iframe[_ngcontent-%COMP%] {\\n  background: white;\\n}\"] });\n/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵsetClassMetadata\"](DocsComponent, [{\n        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"Component\"],\n        args: [{\n                selector: 'jhi-docs',\n                templateUrl: './docs.component.html',\n                styleUrls: ['docs.scss']\n            }]\n    }], null, null); })();\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8vLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL2RvY3MvZG9jcy5jb21wb25lbnQudHM/YzY2MSIsIndlYnBhY2s6Ly8vLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL2RvY3MvZG9jcy5jb21wb25lbnQuaHRtbD9iZmNjIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0FBQUE7QUFBQTtBQUEwQzs7QUFPbkMsTUFBTSxhQUFhOzswRUFBYixhQUFhOzZGQUFiLGFBQWE7UUNQMUIsdUVBQWtJO1FBQUM7OzZGRE90SCxhQUFhO2NBTHpCLHVEQUFTO2VBQUM7Z0JBQ1QsUUFBUSxFQUFFLFVBQVU7Z0JBQ3BCLFdBQVcsRUFBRSx1QkFBdUI7Z0JBQ3BDLFNBQVMsRUFBRSxDQUFDLFdBQVcsQ0FBQzthQUN6QiIsImZpbGUiOiIuL3NyYy9tYWluL3dlYmFwcC9hcHAvYWRtaW4vZG9jcy9kb2NzLmNvbXBvbmVudC50cy5qcyIsInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7IENvbXBvbmVudCB9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xuXG5AQ29tcG9uZW50KHtcbiAgc2VsZWN0b3I6ICdqaGktZG9jcycsXG4gIHRlbXBsYXRlVXJsOiAnLi9kb2NzLmNvbXBvbmVudC5odG1sJyxcbiAgc3R5bGVVcmxzOiBbJ2RvY3Muc2NzcyddXG59KVxuZXhwb3J0IGNsYXNzIERvY3NDb21wb25lbnQge31cbiIsIjxpZnJhbWUgc3JjPVwic3dhZ2dlci11aS9pbmRleC5odG1sXCIgd2lkdGg9XCIxMDAlXCIgaGVpZ2h0PVwiOTAwXCIgc2VhbWxlc3MgdGFyZ2V0PVwiX3RvcFwiIHRpdGxlPVwiU3dhZ2dlciBVSVwiIGNsYXNzPVwiYm9yZGVyLTBcIj48L2lmcmFtZT4gIl0sInNvdXJjZVJvb3QiOiIifQ==\n//# sourceURL=webpack-internal:///./src/main/webapp/app/admin/docs/docs.component.ts\n");

/***/ }),

/***/ "./src/main/webapp/app/admin/docs/docs.module.ts":
/*!*******************************************************!*\
  !*** ./src/main/webapp/app/admin/docs/docs.module.ts ***!
  \*******************************************************/
/*! exports provided: DocsModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, \"DocsModule\", function() { return DocsModule; });\n/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ \"./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js\");\n/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ \"./node_modules/@angular/router/__ivy_ngcc__/fesm2015/router.js\");\n/* harmony import */ var app_shared_shared_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! app/shared/shared.module */ \"./src/main/webapp/app/shared/shared.module.ts\");\n/* harmony import */ var _docs_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./docs.component */ \"./src/main/webapp/app/admin/docs/docs.component.ts\");\n/* harmony import */ var _docs_route__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./docs.route */ \"./src/main/webapp/app/admin/docs/docs.route.ts\");\n\n\n\n\n\n\n\nclass DocsModule {\n}\nDocsModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵdefineNgModule\"]({ type: DocsModule });\nDocsModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵdefineInjector\"]({ factory: function DocsModule_Factory(t) { return new (t || DocsModule)(); }, imports: [[app_shared_shared_module__WEBPACK_IMPORTED_MODULE_2__[\"PharmacySharedModule\"], _angular_router__WEBPACK_IMPORTED_MODULE_1__[\"RouterModule\"].forChild([_docs_route__WEBPACK_IMPORTED_MODULE_4__[\"docsRoute\"]])]] });\n(function () { (typeof ngJitMode === \"undefined\" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵsetNgModuleScope\"](DocsModule, { declarations: [_docs_component__WEBPACK_IMPORTED_MODULE_3__[\"DocsComponent\"]], imports: [app_shared_shared_module__WEBPACK_IMPORTED_MODULE_2__[\"PharmacySharedModule\"], _angular_router__WEBPACK_IMPORTED_MODULE_1__[\"RouterModule\"]] }); })();\n/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵsetClassMetadata\"](DocsModule, [{\n        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"NgModule\"],\n        args: [{\n                imports: [app_shared_shared_module__WEBPACK_IMPORTED_MODULE_2__[\"PharmacySharedModule\"], _angular_router__WEBPACK_IMPORTED_MODULE_1__[\"RouterModule\"].forChild([_docs_route__WEBPACK_IMPORTED_MODULE_4__[\"docsRoute\"]])],\n                declarations: [_docs_component__WEBPACK_IMPORTED_MODULE_3__[\"DocsComponent\"]]\n            }]\n    }], null, null); })();\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8vLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL2RvY3MvZG9jcy5tb2R1bGUudHM/ZTM2ZCJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUF5QztBQUNNO0FBQ2lCO0FBRWY7QUFFUjs7O0FBTWxDLE1BQU0sVUFBVTs7eUZBQVYsVUFBVTs4SUFBVixVQUFVLGtCQUhaLENBQUMsNkVBQW9CLEVBQUUsNERBQVksQ0FBQyxRQUFRLENBQUMsQ0FBQyxxREFBUyxDQUFDLENBQUMsQ0FBQzttSUFHeEQsVUFBVSxtQkFGTiw2REFBYSxhQURsQiw2RUFBb0IsRUFBRTs2RkFHckIsVUFBVTtjQUp0QixzREFBUTtlQUFDO2dCQUNSLE9BQU8sRUFBRSxDQUFDLDZFQUFvQixFQUFFLDREQUFZLENBQUMsUUFBUSxDQUFDLENBQUMscURBQVMsQ0FBQyxDQUFDLENBQUM7Z0JBQ25FLFlBQVksRUFBRSxDQUFDLDZEQUFhLENBQUM7YUFDOUIiLCJmaWxlIjoiLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL2RvY3MvZG9jcy5tb2R1bGUudHMuanMiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBOZ01vZHVsZSB9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xuaW1wb3J0IHsgUm91dGVyTW9kdWxlIH0gZnJvbSAnQGFuZ3VsYXIvcm91dGVyJztcbmltcG9ydCB7IFBoYXJtYWN5U2hhcmVkTW9kdWxlIH0gZnJvbSAnYXBwL3NoYXJlZC9zaGFyZWQubW9kdWxlJztcblxuaW1wb3J0IHsgRG9jc0NvbXBvbmVudCB9IGZyb20gJy4vZG9jcy5jb21wb25lbnQnO1xuXG5pbXBvcnQgeyBkb2NzUm91dGUgfSBmcm9tICcuL2RvY3Mucm91dGUnO1xuXG5ATmdNb2R1bGUoe1xuICBpbXBvcnRzOiBbUGhhcm1hY3lTaGFyZWRNb2R1bGUsIFJvdXRlck1vZHVsZS5mb3JDaGlsZChbZG9jc1JvdXRlXSldLFxuICBkZWNsYXJhdGlvbnM6IFtEb2NzQ29tcG9uZW50XVxufSlcbmV4cG9ydCBjbGFzcyBEb2NzTW9kdWxlIHt9XG4iXSwic291cmNlUm9vdCI6IiJ9\n//# sourceURL=webpack-internal:///./src/main/webapp/app/admin/docs/docs.module.ts\n");

/***/ }),

/***/ "./src/main/webapp/app/admin/docs/docs.route.ts":
/*!******************************************************!*\
  !*** ./src/main/webapp/app/admin/docs/docs.route.ts ***!
  \******************************************************/
/*! exports provided: docsRoute */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, \"docsRoute\", function() { return docsRoute; });\n/* harmony import */ var _docs_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./docs.component */ \"./src/main/webapp/app/admin/docs/docs.component.ts\");\n\nconst docsRoute = {\n    path: '',\n    component: _docs_component__WEBPACK_IMPORTED_MODULE_0__[\"DocsComponent\"],\n    data: {\n        pageTitle: 'API'\n    }\n};\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8vLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL2RvY3MvZG9jcy5yb3V0ZS50cz9kODhiIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUVBO0FBQUE7QUFBQTtBQUFpRDtBQUUxQyxNQUFNLFNBQVMsR0FBVTtJQUM5QixJQUFJLEVBQUUsRUFBRTtJQUNSLFNBQVMsRUFBRSw2REFBYTtJQUN4QixJQUFJLEVBQUU7UUFDSixTQUFTLEVBQUUsS0FBSztLQUNqQjtDQUNGLENBQUMiLCJmaWxlIjoiLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL2RvY3MvZG9jcy5yb3V0ZS50cy5qcyIsInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7IFJvdXRlIH0gZnJvbSAnQGFuZ3VsYXIvcm91dGVyJztcblxuaW1wb3J0IHsgRG9jc0NvbXBvbmVudCB9IGZyb20gJy4vZG9jcy5jb21wb25lbnQnO1xuXG5leHBvcnQgY29uc3QgZG9jc1JvdXRlOiBSb3V0ZSA9IHtcbiAgcGF0aDogJycsXG4gIGNvbXBvbmVudDogRG9jc0NvbXBvbmVudCxcbiAgZGF0YToge1xuICAgIHBhZ2VUaXRsZTogJ0FQSSdcbiAgfVxufTtcbiJdLCJzb3VyY2VSb290IjoiIn0=\n//# sourceURL=webpack-internal:///./src/main/webapp/app/admin/docs/docs.route.ts\n");

/***/ })

}]);