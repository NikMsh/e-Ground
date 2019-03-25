import {Injectable} from '@angular/core';
import {AnyAction} from 'redux';
import {ActionsObservable} from 'redux-observable';
import {of} from 'rxjs';
import {catchError, map, switchMap} from 'rxjs/operators';
import {
  LOGIN_USER,
  loginUserFailedAction,
  LOGOUT_USER,
  UPDATE_BALANCE,
  updateBalanceSuccessAction,
  updateCurrentUserAction
} from '../actions/current-user.actions';
import {AuthenticationService} from '../../services/authentication.service';
import {UserService} from '../../services/user.service';
import {GlobalUserStorageService} from '../../services/global-storage.service';
import {AccountService} from '../../services/account.service';
import {ChatServerService} from '../../services/chat-server.service';
import {NotifierService} from 'angular-notifier';
import {updateAccountSuccessAction} from "../actions/accounts.actions";

@Injectable()
export class CurrentUserEpic {
  constructor(private authService: AuthenticationService, private localStorageService: GlobalUserStorageService,
              private accountService: AccountService,
              private chatService: ChatServerService, private notifierService: NotifierService) {
  }
  loginUser$ = (action$: ActionsObservable<AnyAction>) => {
    return action$.ofType(LOGIN_USER).pipe(
      switchMap(({payload}) => {
        return this.authService
          .login(payload.credential)
          .pipe(
            map(user => {
              this.localStorageService.currentUser = {...user};
              this.chatService.connect(user.token.accessToken, user.account.id);
              return updateCurrentUserAction(user);
            }), catchError(error => of(loginUserFailedAction(error)))
          );
      })
    );
  };

  logout$ = (action$: ActionsObservable<AnyAction>) => {
    return action$.ofType(LOGOUT_USER).pipe(
      switchMap(({}) => {
        this.localStorageService.currentUser = null;
        this.chatService.disconnect();
        return of(updateCurrentUserAction(null));
      })
    );
  };

  updateBalance$ = (action$: ActionsObservable<AnyAction>) => {
    return action$.ofType(UPDATE_BALANCE).pipe(
      switchMap(({payload}) => {
        return this.accountService
          .updateAccountBalance(payload.accountId, payload.currentBalance)
          .pipe(
            map(balance => {
              this.localStorageService.currentUser = {
                ...this.localStorageService.currentUser,
                account: {...this.localStorageService.currentUser.account, balance: balance}
              };
              this.notifierService.notify('success', 'Balance was updated successful');
              return updateBalanceSuccessAction(balance);
            }),
            catchError(error => {
              this.notifierService.notify('error', 'Your balance was not updated');
              return error;
            })
          );
      })
    );
  };

  // verifyUserEmail$ = (action$: ActionsObservable<AnyAction>) => {
  //   return action$.ofType(VERIFY_USER_EMAIL).pipe(
  //     switchMap(({ payload }) => {
  //       return this.userService
  //         .verifyEmail(payload)
  //         .pipe(
  //           map(account => updateAccountSuccessAction(account))
  //         );
  //     })
  //   );
  // }

}
