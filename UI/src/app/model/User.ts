import {Account} from './Account';


//???????? why interface ??
export interface User {
  account: Account;
  id: string;
  login: string;
  password?: string;
  email?: string;
  token?: Token;
}

export const defaultUser: User = {
  id: null,
  login: '',
  password: '',
  email: '',
  token: {
    accessToken: '',
    type: ''
  },
  account: {
    name: '',
    surname: '',
    age: 0,
    phoneNumber: '',
  }
};
