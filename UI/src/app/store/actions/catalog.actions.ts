import {Offer} from '../../model/Offer';

export const FETCH_OFFERS = 'FETCH_OFFERS';
export const FETCH_OFFERS_SUCCESS = 'FETCH_OFFERS_SUCCESS';
export const FETCH_OFFERS_FAILED = 'FETCH_OFFERS_FAILED';
export const SELECT_OFFER = 'SELECT_OFFER';
export const SELECT_OFFER_SUCCESS = 'SELECT_OFFER_SUCCESS';
export const SELECT_OFFER_FAILED = 'SELECT_OFFER_FAILED';

export function fetchOffersAction() {
  return {
    type: FETCH_OFFERS
  };
}

export function fetchOffersSuccessAction(offers: Map<string, Offer>) {
  return {
    type: FETCH_OFFERS_SUCCESS,
    payload: {offers}
  };
}

export function fetchOffersFailedAction(message: string) {
  return {
    type: FETCH_OFFERS_FAILED,
    error: true,
    payload: {message}
  };
}

export function selectOfferAction(offerId: string) {
  return {
    type: SELECT_OFFER,
    payload: {offerId}
  };
}

export function selectOfferSuccessAction(offer: Offer) {
  return {
    type: SELECT_OFFER_SUCCESS,
    payload: {offer}
  };
}

export function selectOfferFailedAction(message: string) {
  return {
    type: SELECT_OFFER_FAILED,
    error: true,
    payload: {message}
  };
}
