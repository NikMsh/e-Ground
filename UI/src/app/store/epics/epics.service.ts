import {Injectable} from '@angular/core';
import {combineEpics} from 'redux-observable';
import {CatalogEpic} from './catalog.epic';
import {OfferEpic} from './offer.epic';
import {AccountEpic} from './account.epic';
import {ResetPasswordEpic} from './reset-password.epic';
import {CurrentUserEpic} from './current-user.epic';

@Injectable()
export class EpicService {

  constructor(private catalogEpic: CatalogEpic,
              private offerEpic: OfferEpic,
              private currentUserEpic: CurrentUserEpic,
              private accountEpic: AccountEpic,
              private resetPasswordEpic: ResetPasswordEpic) {
  }

  getEpics() {
    return combineEpics(
      this.catalogEpic.fetchOffers$,
      this.offerEpic.createOffer$,
      this.offerEpic.selectOffer$,
      this.currentUserEpic.loginUser$,
      this.currentUserEpic.logout$,
      this.accountEpic.fetchUser$,
      this.accountEpic.updateAccount$,
      this.resetPasswordEpic.sendEmail$,
      this.resetPasswordEpic.resetPassword$
    );
  }
}
