import {Injectable} from '@angular/core';
import {combineEpics} from 'redux-observable';
import {CatalogEpic} from './catalog.epic';
import {OfferEpic} from './offer.epic';
import {AccountEpic} from './account.epic';

@Injectable()
export class EpicService {

  constructor(private catalogEpic: CatalogEpic,
              private offerEpic: OfferEpic,
              private accountEpic: AccountEpic) {
  }

  getEpics() {
    return combineEpics(
      this.catalogEpic.fetchOffers$,
      this.offerEpic.createOffer$,
      this.offerEpic.selectOffer$,
      this.accountEpic.fetchUser$,
      this.accountEpic.updateAccount$
    );
  }
}
