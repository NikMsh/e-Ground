import {Injectable} from '@angular/core';
import {CatalogService} from '../../services/catalog.service';
import {NgRedux} from '@angular-redux/store';
import {AppState} from '../index';
import {ActionsObservable} from 'redux-observable';
import {AnyAction} from 'redux';
import {catchError, map, switchMap} from 'rxjs/operators';
import {CREATE_OFFER, createOfferFailedAction, createOfferSuccessAction} from '../actions/offer.actions';
import {of} from 'rxjs';
import {SELECT_OFFER, selectOfferFailedAction, selectOfferSuccessAction} from '../actions/catalog.actions';
import {OfferService} from '../../services/offer.service';
import {defaultOffer} from '../../model/Offer';

@Injectable()
export class OfferEpic {
  constructor(private catalogService: CatalogService,
              private ngRedux: NgRedux<AppState>,
              private offerService: OfferService) {
  }

  createOffer$ = (action$: ActionsObservable<AnyAction>) => {
    return action$.ofType(CREATE_OFFER).pipe(
      switchMap(({payload}) => {
        return this.catalogService
          .createOffer(payload.offer)
          .pipe(
            map(offer => {
              return createOfferSuccessAction(offer);
            }),
            catchError(error => {
              return of(createOfferFailedAction(error));
            })
          );
      })
    );
  }

  selectOffer$ = (action$: ActionsObservable<AnyAction>) => {
    return action$.ofType(SELECT_OFFER).pipe(
      switchMap(({payload}) => {
        return payload.offerId !== null ?
          this.offerService
            .getOfferById(payload.startupId)
            .pipe(
              map(startup => selectOfferSuccessAction(startup)),
              catchError(error => {
                return of(selectOfferFailedAction(error));
              })
            )
          : of(defaultOffer)
            .pipe(
              map(startup => selectOfferSuccessAction(startup)),
              catchError(error => {
                return of(selectOfferFailedAction(error));
              })
            );
      })
    );
  }

}

