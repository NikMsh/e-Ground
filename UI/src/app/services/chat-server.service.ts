import { Injectable } from '@angular/core';
import {NgRedux} from '@angular-redux/store';
import {AppState} from '../store';
import {fetchConversationsAction} from '../store/actions/conversation.action';
import {updateMessagesAction} from '../store/actions/message.action';
import {Message} from '../model/Message';
import * as io from 'socket.io-client';

@Injectable({
  providedIn: 'root'
})
export class ChatServerService {

  private socket;

  private url: 'http://localhost:10000';

  constructor(private ngRedux: NgRedux<AppState>) {
  }

  public connect(token: string, userId: string) {
    this.socket = io('http://localhost:10000', {
      query: {
        token,
        userId
      }
    });

    this.socket.on('new_message', (message) => {
      if (this.ngRedux.getState().conversationsState.conversations.size === 0 ||
        this.ngRedux.getState().conversationsState.conversations.get(message.conversationId) === null) {
        this.ngRedux.dispatch(fetchConversationsAction(this.ngRedux.getState().currentUserState.currentUser.account.id));
      } else {
        this.ngRedux.dispatch(updateMessagesAction(message));
      }
    });
  }

  public disconnect() {
    this.socket.disconnect();
  }

  public sendMessage(message: Message) {
    this.socket.emit('new_message', message, (answer) => {
      if (answer === 1) {
        this.ngRedux.dispatch(updateMessagesAction(message));
      }
    });
  }
}
