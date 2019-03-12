import {Injectable} from '@angular/core';
import {combineEpics} from 'redux-observable';
import {CatalogEpic} from './catalog.epic';
import {OfferEpic} from './offer.epic';

@Injectable()
export class EpicService {

  constructor(private catalogEpic: CatalogEpic,
              private offerEpic: OfferEpic) {
  }

  getEpics() {
    return combineEpics(
      this.catalogEpic.fetchOffers$,
      this.offerEpic.createOffer$,
      this.offerEpic.selectOffer$
    );
  }
}
