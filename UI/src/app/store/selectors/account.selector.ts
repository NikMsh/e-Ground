import {AppState} from '../index';

export const isLoading = (state: AppState) => state.accountPageState.isLoading;

export const selectCurrentUser = (state: AppState) => state.accountPageState.user;
