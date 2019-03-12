import {AppState} from '../index';

export const isSelected = (state: AppState) => state.offerPageState.isSelected;

export const selectOffer = (state: AppState) => state.offerPageState.offer;
