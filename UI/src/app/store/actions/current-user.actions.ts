import {User} from '../../model/User';
import {Credential} from '../../model/Credential';

export const UPDATE_CURRENT_USER = '[Current user] Update current user';
export const LOGIN_USER = '[Current user] Login user';
export const LOGIN_USER_FAILED = '[Current user] Login user failed';
export const LOGOUT_USER = '[Current user] Logout user';
export const CLEAR_USER_ERROR_MESSAGE = '[Current user] Error message cleared';

export function loginUserAction(credential: Credential) {
  return {
    type: LOGIN_USER,
    payload: {credential}
  };
}

export function loginUserFailedAction(errorMessage: string) {
  return {
    type: LOGIN_USER_FAILED,
    payload: {errorMessage}
  };
}

export function logoutUserAction() {
  return {
    type: LOGOUT_USER
  };
}

export function updateCurrentUserAction(user: User) {
  return {
    type: UPDATE_CURRENT_USER,
    payload: {user}
  };
}

export function clearUserErrorMessage() {
  return {
    type: CLEAR_USER_ERROR_MESSAGE
  };
}
