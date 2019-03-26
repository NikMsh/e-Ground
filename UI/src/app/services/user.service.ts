import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {User} from '../model/User';
import {Observable, throwError} from "rxjs/index";
import {catchError} from "rxjs/internal/operators";



@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) { }

  register(user: User) {
    return this.http.post(`/api/auth/signup`, user);
  }

  verifyEmail(token: string): Observable<any> {
    return this.http.post<any>('/api/user/verifyEmail',
      null, {
        params: new HttpParams().set('token', token)
      }).pipe(catchError((error: any) => throwError(error.error)));
  }
}
