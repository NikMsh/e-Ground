import {Injectable} from '@angular/core';
import {CatalogService} from '../../services/catalog.service';
import {NgRedux} from '@angular-redux/store';
import {AppState} from '../index';
import {ActionsObservable} from 'redux-observable';
import {AnyAction} from 'redux';
import {catchError, map, switchMap} from 'rxjs/operators';
import {FETCH_OFFERS, fetchOffersFailedAction, fetchOffersSuccessAction} from '../actions/catalog.actions';
import {TransformService} from '../../utils/transform.service';
import {of} from 'rxjs';


@Injectable()
export class CatalogEpic {
  constructor(private catalogService: CatalogService,
              private ngRedux: NgRedux<AppState>) {
  }

  fetchOffers$ = (action$: ActionsObservable<AnyAction>) => {
    return action$.ofType(FETCH_OFFERS).pipe(
      switchMap(({}) => {
        return this.catalogService.getCatalogList().pipe(
           map(offers => fetchOffersSuccessAction(TransformService.transformToMap(offers))),
            catchError(error => {
              return of(fetchOffersFailedAction(error));
            })
        );
      })
    );
  };
}
