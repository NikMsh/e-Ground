import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import thunkMiddlware from 'redux-thunk';
import {createLogger} from 'redux-logger';
import {AppComponent} from './app.component';
import {DevToolsExtension, NgRedux, NgReduxModule} from '@angular-redux/store';
import {AppState} from './store';
import {NgReduxRouter, NgReduxRouterModule} from '@angular-redux/router';
import {createEpicMiddleware} from 'redux-observable';
import {EpicService} from './store/epics/epics.service';
import {reducers} from './store/reducers/reducers';
import {MaterialModule} from './material';
import {
  MatButtonModule,
  MatCardModule,
  MatChipsModule, MatDatepickerModule,
  MatDialogModule,
  MatIconModule, MatInputModule,
  MatListModule,
  MatMenuModule, MatNativeDateModule, MatProgressBarModule
} from '@angular/material';
import {FlexLayoutModule} from '@angular/flex-layout';
import {EpicsModule} from './store/epics/epics.module';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgxPermissionsModule} from 'ngx-permissions';
import {NotifierModule, NotifierOptions} from 'angular-notifier';
import {GlobalUserStorageService} from './services/global-user-storage.service';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { UserSidenavComponent } from './components/user-sidenav/user-sidenav.component';
import { CatalogComponent } from './components/catalog/catalog.component';
import { OfferEditComponent } from './components/offer-edit/offer-edit.component';
import { OfferComponent } from './components/offer/offer.component';
import { ImageUploadComponent } from './components/image-upload/image-upload.component';
import {OfferFeedbackComponent} from './components/offer-feedback/offer-feedback.component';
import {CatalogSearchToolbarComponent} from './components/catalog-search-toolbar/catalog-search-toolbar.component';
import { AccountComponent } from './components/account/account.component';
import { AccountEditComponent } from './components/account-edit/account-edit.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import {DialogsModule} from './components/dialogs/dialogs.module';

const customNotifierOptions: NotifierOptions = {
  position: {
    horizontal: {
      position: 'right',
      distance: 20
    },
    vertical: {
      position: 'bottom',
      distance: 60,
      gap: 10
    }
  },
  theme: 'material',
  behaviour: {
    autoHide: 2500,
    onClick: 'hide',
    onMouseover: 'pauseAutoHide',
    showDismissButton: true,
    stacking: 4
  },
  animations: {
    enabled: true,
    show: {
      preset: 'slide',
      speed: 300,
      easing: 'ease'
    },
    hide: {
      preset: 'fade',
      speed: 300,
      easing: 'ease',
      offset: 50
    },
    shift: {
      speed: 300,
      easing: 'ease'
    },
    overlap: 150
  }
};

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    MainPageComponent,
    UserSidenavComponent,
    CatalogComponent,
    OfferEditComponent,
    OfferComponent,
    CatalogSearchToolbarComponent,
    OfferFeedbackComponent,
    ImageUploadComponent,
    AccountComponent,
    AccountEditComponent,
    ResetPasswordComponent
  ],
  imports: [
    BrowserModule,
    EpicsModule,
    NgReduxModule,
    NgReduxRouterModule.forRoot(),
    HttpClientModule,
    FormsModule,
    DialogsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MaterialModule,
    MatChipsModule,
    MatDialogModule,
    MatListModule,
    FlexLayoutModule,
    MaterialModule,
    MatMenuModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MaterialModule,
    MatMenuModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatProgressBarModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    FlexLayoutModule,
    NgxPermissionsModule.forRoot(),
    NotifierModule.withConfig(customNotifierOptions)
  ],
  providers: [EpicService],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private ngRedux: NgRedux<AppState>,
              private ngReduxRouter: NgReduxRouter,
              private epicService: EpicService,
              private devTools: DevToolsExtension,
              private storageService: GlobalUserStorageService) {
    const epics = this.epicService.getEpics();
    const middleware = createEpicMiddleware();
    let enhancers = [];
    if (devTools.isEnabled()) {
      enhancers = [devTools.enhancer()];
    }
    ngRedux.configureStore(reducers, this.storageService.getInitialState(), [middleware, thunkMiddlware, createLogger()], enhancers);
    middleware.run(epics as any);
    ngReduxRouter.initialize((state: AppState) => state.router);
  }
}
