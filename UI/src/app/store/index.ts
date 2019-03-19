import {CurrentUserState} from './reducers/current-user.reducer';
import {UserSideNavState} from './reducers/user-side-nav.reducer';
import {CatalogState} from './reducers/catalog.reducer';
import {OfferPageState} from './reducers/offer-page.reducer';
import {AccountPageState} from './reducers/account-page.reducer';

export interface AppState {
  readonly router?: string;
  readonly currentUserState?: CurrentUserState;
  readonly userSideNavState?: UserSideNavState;
  readonly catalogState?: CatalogState;
  readonly offerPageState?: OfferPageState;
  readonly accountPageState?: AccountPageState;
  /*readonly startupsState?: StartupsState;
  readonly startupPageState?: StartupPageState;
  readonly router?: string;
  readonly dialogsState?: DialogState;
  readonly currentUserState?: CurrentUserState;
  readonly startupSearchToolbarState?: StartupSearchToolbarState;
  readonly resumeState?: ResumeState;
  readonly resumePageState?: ResumePageState;
  readonly userSideNavState?: UserSideNavState;
  readonly contactsState?: ContactsState;
  readonly accountPageState?: AccountPageState;
  readonly specialistsSearchState?: SpecialistsSearchToolbarState;
  readonly conversationsState?: ConversationsState;
  readonly favoritesState?: FavoritesState;
  readonly resetPasswordState?: ResetPasswordState;
  readonly contactsSearchToolbarState?: ContactsSearchToolbarState;*/
}


