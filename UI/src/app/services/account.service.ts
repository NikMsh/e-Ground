import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {User} from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  apiUrl = '/api/v1/processor';

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

  updateAccount(detailAccountDTO: any): Observable<User> {
    console.log(detailAccountDTO);
    console.log('update account');
    /*const options = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.put<any>(this.accountUrl + 'update/' + detailAccountDTO.id, detailAccountDTO, options)
      .pipe(catchError((error: any) => throwError(error.error)));*/
    return of(this.user);
  }

  getUserById(id: string): Observable<User> {
    /*return this.http.get<User>(`${this.apiUrl}/customers/${id}`)
      .pipe(catchError((error: any) => throwError(error.error)));*/
    return of(this.user);
  }
}
