import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {User} from '../model/User';
import {Observable, of, throwError} from 'rxjs/index';
import {RegistrationData} from '../model/RegistrationData';
import {catchError} from 'rxjs/operators';


@Injectable({providedIn: 'root'})
export class UserService {

  constructor(private http: HttpClient) {
  }

  user: User = {
    account: {
      name: 'Kirill',
      surname: 'Friend',
      age: 303,
      phoneNumber: '2144231423'
    },
    id: '1',
    password: 'q1w2e3',
    email: 'kirill@mail.ru',
    token: {
      accessToken: 'asddas',
      type: 'adsfasdf'
    }
  };

  register(registrationData: RegistrationData): Observable<User> {
    return this.http.post<User>(`/api/v1/processor/customers`, registrationData);
    // return of(this.user);
  }

  verifyEmail(token: string): Observable<any> {
    return this.http.post<any>('/api/user/verifyEmail',
      null, {
        params: new HttpParams().set('token', token)
      }).pipe(catchError((error: any) => throwError(error.error)));
  }
}
