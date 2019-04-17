import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {Conversation} from '../model/Conversation';
import {HttpClient} from '@angular/common/http';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ConversationService {
  conversationUrl = 'api/conversation';

  constructor(private http: HttpClient) {
  }

  getUserConversations(id: string): Observable<Conversation[]> {
    return this.http.get<Conversation[]>(`${this.conversationUrl}/${id}`)
      .pipe(catchError((error: any) => throwError(error)));
  }

  getConversationByUsersIds(yourId: string, otherId: string) {
    return this.http.get<Conversation>(`${this.conversationUrl}/getConversationInfoByUsersIds`, {
      params: {yourId, otherId}
    })
      .pipe(catchError((error: any) => throwError(error)));
  }
}
