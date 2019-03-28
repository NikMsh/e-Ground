import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {User} from '../model/User';
import {Observable, of, throwError} from 'rxjs/index';
import {catchError} from 'rxjs/internal/operators';
import {RegistrationData} from '../model/RegistrationData';



@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) { }

  user: User = {
    id: '213',
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

  register(registrationData: RegistrationData) {
    // return this.http.post(`/api/auth/signup`, user);
    console.log('Registration is successful');
    console.log(registrationData);
    return of(this.user);
  }

  verifyEmail(token: string): Observable<any> {
    return this.http.post<any>('/api/user/verifyEmail',
      null, {
        params: new HttpParams().set('token', token)
      }).pipe(catchError((error: any) => throwError(error.error)));
  }
}
