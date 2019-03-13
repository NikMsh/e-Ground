import {Offer} from '../../model/Offer';
import {Reducer} from 'redux';
import {CREATE_OFFER, CREATE_OFFER_FAILED, CREATE_OFFER_SUCCESS} from '../actions/offer.actions';
import {SELECT_OFFER, SELECT_OFFER_FAILED, SELECT_OFFER_SUCCESS} from '../actions/catalog.actions';


export interface OfferPageState {
  readonly offer: Offer;
  readonly isSelected: boolean;
}

const INITIAL_STATE = {
  offer: null,
  isSelected: false
};

export const offerPageReducer: Reducer<OfferPageState> = (state: OfferPageState = INITIAL_STATE, action) => {
  switch (action.type) {
    case CREATE_OFFER: {
      return {...state, isSelected: true};
    }
    case CREATE_OFFER_SUCCESS: {
      return {...state, isSelected: false, offer: action.payload.offer};
    }
    case CREATE_OFFER_FAILED: {
      return {...state, isSelected: false};
    }
    case SELECT_OFFER: {
      return {...state, isSelected: true};
    }
    case SELECT_OFFER_SUCCESS: {
      return {...state, offer: action.payload.offer, isSelected: false};
    }
    case SELECT_OFFER_FAILED: {
      return {...state, isSelected: false};
    }
    default: {
      return state;
    }
  }
};
