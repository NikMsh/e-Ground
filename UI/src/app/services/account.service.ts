import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {User} from '../model/User';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  apiUrl = '/api/v1/processor';
  accountUrl = '/api/account/';

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


  constructor(private http: HttpClient) {
  }

  updateAccount(detailAccountDTO: any): Observable<any> {
    console.log(detailAccountDTO);
    /*const options = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.put<any>(this.accountUrl + 'update/' + detailAccountDTO.id, detailAccountDTO, options)
      .pipe(catchError((error: any) => throwError(error.error)));*/
    return of(this.user);
  }

  getUserById(id: string): Observable<User> {
    // return this.http.get<User>(`${this.apiUrl}/customers/${id}`)
      // .pipe(catchError((error: any) => throwError(error.error)));
    return of(this.user);
  }
}
