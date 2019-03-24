import {NgModule} from '@angular/core';
import {CatalogEpic} from './catalog.epic';
import {OfferEpic} from './offer.epic';
import {AccountEpic} from './account.epic';

@NgModule({
  providers: [
    CatalogEpic,
    OfferEpic,
    AccountEpic
  ],
})
export class EpicsModule {
}
