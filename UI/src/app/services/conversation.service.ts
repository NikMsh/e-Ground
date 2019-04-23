import {Injectable} from '@angular/core';
import {Observable, of, throwError} from 'rxjs';
import {Conversation} from '../model/Conversation';
import {HttpClient} from '@angular/common/http';
import {catchError} from 'rxjs/operators';
import {Account} from "../model/Account";
import {Message} from "../model/Message";

@Injectable({
  providedIn: 'root'
})
export class ConversationService {
  conversationUrl = 'api/conversation';

  account1: Account = {
  id: '1',
  firstName: 'Kirill',
  lastName: 'Petrov',
  age: 20,
  phoneNumber: '911'
 };

  account2: Account = {
    id: '2',
    firstName: 'Evgeniy',
    lastName: 'Garkavik',
    age: 20,
    phoneNumber: '912'
  };

  messages: Message[] = [ {
  conversationId: '1',
  senderId: '1',
  receiverId: '2',
  msg: 'Hello',
  creationDate: new Date(2019,4,17)
 }, {
    conversationId: '2',
    senderId: '2',
    receiverId: '1',
    msg: 'Hello',
    creationDate: new Date(2019, 4,17)
  } ];

  conversation: Conversation = {
  id: '1',
  firstAccount: this.account1,
  secondAccount: this.account2,
  name: 'firstConversation',
  conversationMessages: this.messages
 };

  constructor(private http: HttpClient) {
  }

  getUserConversations(id: string): Observable<Conversation[]> {
    console.log('Hello conversation');
    /*return this.http.get<Conversation[]>(`${this.conversationUrl}/${id}`)
      .pipe(catchError((error: any) => throwError(error)));*/
    return of([this.conversation]);
  }

  getConversationByUsersIds(yourId: string, otherId: string) {
    console.log('Hello conversation');
    /*return this.http.get<Conversation>(`${this.conversationUrl}/getConversationInfoByUsersIds`, {
      params: {yourId, otherId}
    })
      .pipe(catchError((error: any) => throwError(error)));
  }*/
    return of(this.conversation);
  }
}
