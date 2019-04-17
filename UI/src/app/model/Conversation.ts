import {Message} from './Message';
import {Account} from './Account';

export class Conversation {
  id: string;
  firstAccount: Account;
  secondAccount: Account;
  name: string;
  conversationMessages: Message[];
}
