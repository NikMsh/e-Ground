import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {MainPageComponent} from './components/main-page/main-page.component';
import {CatalogComponent} from './components/catalog/catalog.component';
import {OfferEditComponent} from './components/offer-edit/offer-edit.component';
import {OfferComponent} from './components/offer/offer.component';
import {AccountComponent} from './components/account/account.component';
import {AccountEditComponent} from './components/account-edit/account-edit.component';
import {ResetPasswordComponent} from './components/reset-password/reset-password.component';
import {AuthGuard} from "./guards/auth.guard";
import {AccountEditGuard} from "./guards/account-edit.guard";

const routes: Routes = [
  {path: '', redirectTo: 'main-page', pathMatch: 'full'},
  {path: 'main-page', component: MainPageComponent},
  {path: 'account/:id', component: AccountComponent},
  {path: 'account-edit/:id', component: AccountEditComponent, canActivate: [AccountEditGuard]},
  {path: 'offer/:id', component: OfferComponent},
  {path: 'offer-edit/:id', component: OfferEditComponent},
  {path: 'catalog', component: CatalogComponent},
  {path: 'changePassword', component: ResetPasswordComponent},
  {path: '**', redirectTo: 'main-page'}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule {
}
