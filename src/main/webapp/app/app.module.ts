import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PharmacySharedModule } from 'app/shared/shared.module';
import { PharmacyCoreModule } from 'app/core/core.module';
import { PharmacyAppRoutingModule } from './app-routing.module';
import { PharmacyHomeModule } from './home/home.module';
import { PharmacyEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    PharmacySharedModule,
    PharmacyCoreModule,
    PharmacyHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PharmacyEntityModule,
    PharmacyAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class PharmacyAppModule {}
