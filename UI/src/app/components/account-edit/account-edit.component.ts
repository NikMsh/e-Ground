import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs/internal/Subscription';
import {Observable} from 'rxjs/index';
import {isLoading, selectCurrentUser, selectAccountForEdit} from '../../store/selectors/account.selector';
import {fetchUserAction, updateAccountAction} from '../../store/actions/account.actions';
import {NgRedux, select} from '@angular-redux/store';
import {User} from '../../model/User';
import {skipWhile, take} from 'rxjs/internal/operators';
import {ActivatedRoute} from '@angular/router';
import {AppState} from '../../store';
import {AccountService} from '../../services/account.service';
import {updateRouterState} from '../../store/actions/router.actions';
import {Location} from '@angular/common';

@Component({
  selector: 'app-account-edit',
  templateUrl: './account-edit.component.html',
  styleUrls: ['./account-edit.component.css']
})
export class AccountEditComponent implements OnInit, OnDestroy {
  userId: string;
  accountForm: FormGroup;

  subscription: Subscription;

  updatedAccount: User;
  isCompareDateError: boolean;

  @select(isLoading)
  isLoading: Observable<boolean>;

  @select(selectCurrentUser)
  user: Observable<User>;

  constructor(private accountService: AccountService,
              private route: ActivatedRoute,
              private ngRedux: NgRedux<AppState>,
              private formBuilder: FormBuilder,
              private location: Location, ) { }

  ngOnInit() {
    this.isCompareDateError = false;
    this.userId = this.route.snapshot.paramMap.get('id');
    this.ngRedux.dispatch(fetchUserAction(this.userId));
    // this.ngRedux.dispatch(selectAccount(this.userId));
    this.isLoading.pipe(skipWhile(result => result), take(1))
      .subscribe(() => this.ngRedux.select(state => selectAccountForEdit(state))
        .subscribe(user => {
          this.updatedAccount = user;
          this.initializeForm(user);
        }));
  }

  private initializeForm(user: User) {
    this.accountForm = this.formBuilder.group({
        name: [user.account.name, [Validators.required, Validators.maxLength(35), Validators.pattern(/^[A-z0-9]*$/)]],
        surname: [user.account.surname, [Validators.maxLength(35), Validators.pattern(/^[A-z0-9]*$/)]],
        age: [user.account.age],
        phoneNumber: [user.account.phoneNumber]
      }
    );
  }

  changeAccount(form: FormGroup): User {
    this.updatedAccount.account.name = form.getRawValue().name;
    this.updatedAccount.account.surname = form.getRawValue().surname;
    this.updatedAccount.account.age = form.getRawValue().age;
    this.updatedAccount.account.phoneNumber = form.getRawValue().phoneNumber;
    return this.updatedAccount;
  }

  updateAccount(form) {
    this.ngRedux.dispatch(updateAccountAction({...this.changeAccount(form)}));
    this.isLoading.pipe(skipWhile(result => result === true), take(1))
      .subscribe(() => this.ngRedux.dispatch(updateRouterState('/account/' + this.userId)));
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  goBack() {
    this.location.back();
  }
}

