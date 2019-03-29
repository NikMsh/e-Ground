import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {Credential} from '../model/Credential';
import {NgxPermissionsService} from 'ngx-permissions';
import {User} from '../model/User';


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

  login(credential: Credential): Observable<User> {
    // return this.http.post<any>(`/api/auth/signin`, {email: credential.email, password: credential.password})
    // .pipe(catchError((error) => throwError(error)));

    return of(this.user);
  }
}
