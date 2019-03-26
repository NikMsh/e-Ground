import {NgxPermissionsService} from 'ngx-permissions';
import {Component, OnInit} from '@angular/core';
import {NgRedux, select} from '@angular-redux/store';
import {Observable} from 'rxjs';
import {User} from '../../model/User';
import {AppState} from '../../store';
import {selectCurrentUser} from '../../store/selectors/current-user.selector';
import {SignInComponent} from '../dialogs/sign-in/sign-in.component';
import {logoutUserAction} from '../../store/actions/current-user.actions';
import {updateRouterState} from '../../store/actions/router.actions';
import {SignUpComponent} from '../dialogs/sign-up/sign-up.component';
import {showDialogAction} from '../../store/actions/dialogs.actions';
import {hideUserSideNavAction} from '../../store/actions/user-side-nav.actions';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  ls = localStorage;

  @select(selectCurrentUser)
  currentUser: Observable<User>;

  navLinks = [{
    path: '/catalog',
    label: 'Catalog',
    isActive: true
  }];

  constructor(
    private ngRedux: NgRedux<AppState>,
    private  permissionsServive: NgxPermissionsService) {
  }

  ngOnInit() {
  }

  openSingIn(): void {
    this.ngRedux.dispatch(showDialogAction({
      componentType: SignInComponent,
      width: '500px',
      data: null
    }));

  }

  openSingUp(): void {
    this.ngRedux.dispatch(showDialogAction({
      componentType: SignUpComponent,
      width: '500px',
      data: null
    }));
  }

  logout() {
    this.ls.clear();
    this.ngRedux.dispatch(hideUserSideNavAction());
    this.ngRedux.dispatch(logoutUserAction());
    this.ngRedux.dispatch(updateRouterState('/main'));
    this.permissionsServive.flushPermissions();
  }

}
