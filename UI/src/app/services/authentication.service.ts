import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable, of, throwError} from 'rxjs';
import {User} from '../model/User';
import {Credential} from '../model/Credential';
import {NgxPermissionsService} from 'ngx-permissions';
import {catchError} from 'rxjs/operators';


@Injectable({providedIn: 'root'})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient,
              private  permissionsServive: NgxPermissionsService) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

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

  login(credential: Credential) {
    /*return this.http.post<any>(`/api/auth/signin`, {email: credential.email, password: credential.password})
      .pipe(catchError((error) => throwError(error)));*/
    console.log('Hello world');
    console.log(credential);
    return of(this.user);
  }
}
