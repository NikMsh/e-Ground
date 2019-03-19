import {User} from '../../model/User';

export const FETCH_USER = 'FETCH_USER';
export const FETCH_USER_SUCCESS = 'FETCH_USER_SUCCESS';
export const FETCH_USER_FAILED = 'FETCH_USER_FAILED';

export function fetchUserAction(userId: string) {
  return {
    type: FETCH_USER,
    payload: {userId}
  };
}

export function fetchUserSuccessAction(user: User) {
  return {
    type: FETCH_USER_SUCCESS,
    payload: {user}
  };
}

export function fetchUserFailedAction(message: string) {
  return {
    type: FETCH_USER_FAILED,
    error: true,
    payload: {message}
  };
}


