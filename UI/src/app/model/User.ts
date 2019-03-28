import {Account} from './Account';


//???????? why interface ??
export class User {
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
    name: '',
    surname: '',
    age: 0,
    phoneNumber: '',
  }
};
