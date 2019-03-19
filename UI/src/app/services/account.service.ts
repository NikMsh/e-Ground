import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {User} from '../model/User';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  apiUrl = '/api/v1/processor';

  user: User = {
    id: '213',
    login: 'Sicphy',
    password: '1798',
    email: 'kirill@mail.ru',
    token: {
      accessToken: 'aaaaaa',
      type: 'bbbbb'
    },
    account: {
      name: 'kirill',
      surname: 'petrov',
      age: 17,
      phoneNumber: '911',
    }
  };


  constructor(private http: HttpClient) {
  }

  getUserById(id: string): Observable<User> {
    // return this.http.get<User>(`${this.apiUrl}/customers/${id}`)
      // .pipe(catchError((error: any) => throwError(error.error)));
    return of(this.user);
  }
}
