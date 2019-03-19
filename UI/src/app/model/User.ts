import {Account} from './Account';

export interface User {
  account: Account;
  id: string;
  login: string;
  password?: string;
  email?: string;
  token?: Token;
}
