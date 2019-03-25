import {NgModule} from '@angular/core';
import {CatalogEpic} from './catalog.epic';
import {OfferEpic} from './offer.epic';
import {AccountEpic} from './account.epic';
import {ResetPasswordEpic} from './reset-password.epic';

@NgModule({
  providers: [
    CatalogEpic,
    OfferEpic,
    AccountEpic,
    ResetPasswordEpic,
    CurrentUserEpic,
  ],
})
export class EpicsModule {
}
