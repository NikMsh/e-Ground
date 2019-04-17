import {NgModule} from '@angular/core';
import {CatalogEpic} from './catalog.epic';
import {OfferEpic} from './offer.epic';
import {AccountEpic} from './account.epic';
import {ResetPasswordEpic} from './reset-password.epic';
import {CurrentUserEpic} from './current-user.epic';
import {UserEpic} from './user.epic';
import {ConversationsEpic} from './conversations.epic';

@NgModule({
  providers: [
    CatalogEpic,
    OfferEpic,
    AccountEpic,
    ResetPasswordEpic,
    CurrentUserEpic,
    UserEpic,
    ConversationsEpic
  ],
})
export class EpicsModule {
}
