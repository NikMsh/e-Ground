import {Reducer} from 'redux';
import {Offer} from '../../model/Offer';
import {FETCH_OFFERS, FETCH_OFFERS_FAILED, FETCH_OFFERS_SUCCESS} from '../actions/catalog.actions';
import {CREATE_OFFER, CREATE_OFFER_FAILED, CREATE_OFFER_SUCCESS} from '../actions/offer.actions';


export interface CatalogState {
  readonly offers: Map<string, Offer>;
  readonly isLoading: boolean;
}

const INITIAL_STATE = {
  offers: new Map<string, Offer>(),
  isLoading: false
};

export const catalogReducer: Reducer<CatalogState> = (state: CatalogState = INITIAL_STATE, action): CatalogState => {
  switch (action.type) {
    case FETCH_OFFERS: {
      return {...state, isLoading: true};
    }
    case FETCH_OFFERS_SUCCESS: {
      return {...state, ...action.payload, isLoading: false};
    }
    case FETCH_OFFERS_FAILED: {
      return {...state, isLoading: false};
    }
    default: {
      return state;
    }
  }
};
