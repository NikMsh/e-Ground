import {Reducer} from 'redux';
import {User} from '../../model/User';
import {FETCH_USER, FETCH_USER_SUCCESS, FETCH_USER_FAILED} from '../actions/account.actions';

export interface AccountPageState {
  readonly user: User;
  readonly isLoading: boolean;
}

const INITIAL_STATE = {
  user: null,
  isLoading: false
};

export const accountPageReducer: Reducer<AccountPageState> = (state: AccountPageState = INITIAL_STATE, action): AccountPageState => {
  switch (action.type) {
    case FETCH_USER: {
      return {...state, isLoading: true};
    }
    case FETCH_USER_SUCCESS: {
      return {...state, ...action.payload, isLoading: false};
    }
    case FETCH_USER_FAILED: {
      return {...state, isLoading: false};
    }
    default: {
      return state;
    }
  }
};
//object.assign
