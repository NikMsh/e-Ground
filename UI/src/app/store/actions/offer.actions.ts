import {Offer} from '../../model/Offer';

export const CREATE_OFFER = 'CREATE_OFFER';
export const CREATE_OFFER_SUCCESS = 'CREATE_OFFER_SUCCESS';
export const CREATE_OFFER_FAILED = 'CREATE_OFFER_FAILED';


export function createOfferAction(offer: Offer) {
  return {
    type: CREATE_OFFER,
    payload: {offer}
  };
}

export function createOfferSuccessAction(offer: Offer) {
  return {
    type: CREATE_OFFER_SUCCESS,
    payload: {offer}
  };
}

export function createOfferFailedAction(errorMessage: string) {
  return {
    type: CREATE_OFFER_FAILED,
    error: true,
    payload: {errorMessage}
  };
}
