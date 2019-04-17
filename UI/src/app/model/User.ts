import {Account} from './Account';
import {Token} from './Token';

export interface User {
  account: Account;
  id: string;
  password?: string;
  email?: string;
  token?: Token;
}

export const defaultUser: User = {
  id: null,
  password: '',
  email: '',
  token: {
    accessToken: '',
    type: ''
  },
  account: {
    id: '',
    firstName: '',
    lastName: '',
    age: 0,
    phoneNumber: '',
  }
};
