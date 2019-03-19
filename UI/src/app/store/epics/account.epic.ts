import {Injectable} from '@angular/core';
import {AccountService} from '../../services/account.service';
import {NgRedux} from '@angular-redux/store';
import {AppState} from '../index';
import {ActionsObservable} from 'redux-observable';
import {AnyAction} from 'redux';
import {catchError, map, switchMap} from 'rxjs/operators';
import {FETCH_USER, fetchUserFailedAction, fetchUserSuccessAction} from '../actions/account.actions';
import {of} from 'rxjs';


@Injectable()
export class AccountEpic {
  constructor(private accountService: AccountService,
              private ngRedux: NgRedux<AppState>) {
  }

  fetchUser$ = (action$: ActionsObservable<AnyAction>) => {
    return action$.ofType(FETCH_USER).pipe(
      switchMap(({payload}) => {
        return this.accountService.getUserById(payload.userId)
            .pipe(map(user => fetchUserSuccessAction(user)),
              catchError(error => {
                return of(fetchUserFailedAction(error));
              })
            );
      })
    );
  }
}
