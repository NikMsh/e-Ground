import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../../services/user.service';
import {first} from 'rxjs/internal/operators';
import {DialogResult} from '../../../model/dialog-result';
import {NgRedux} from '@angular-redux/store';
import {AppState} from '../../../store';
import {NotifierService} from 'angular-notifier';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  registerForm: FormGroup;
  loading = false;
  submitted = false;
  error = '';

  constructor(private fb: FormBuilder,
              public dialogRef: MatDialogRef<SignUpComponent>,
              private userService: UserService,
              private ngRedux: NgRedux<AppState>,
              private notifier: NotifierService,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
    this.initializeForm();
  }

  private initializeForm() {
    this.registerForm = this.fb.group({
      name: ['', [Validators.maxLength(40)]],
      surname: ['', [Validators.maxLength(40)]],
      age: ['', [Validators.maxLength(3)]],
      phoneNumber: ['', [Validators.minLength(6), Validators.maxLength(20)]],
      login: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(15)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(40)]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]]
    }, {validator: this.checkPasswords});
  }

  checkPasswords(group: FormGroup) {
    return group.get('password').value === group.get('confirmPassword').value ? null : {notSame: true};
  }

  get name(): FormControl {
    return this.registerForm.get('name') as FormControl;
  }

  get surname(): FormControl {
    return this.registerForm.get('surname') as FormControl;
  }

  get age(): FormControl {
    return this.registerForm.get('age') as FormControl;
  }

  get phoneNumber(): FormControl {
    return this.registerForm.get('phoneNumber') as FormControl;
  }

  get login(): FormControl {
    return this.registerForm.get('login') as FormControl;
  }

  get email(): FormControl {
    return this.registerForm.get('email') as FormControl;
  }

  get password(): FormControl {
    return this.registerForm.get('password') as FormControl;
  }

  get confirmPassword(): FormControl {
    return this.registerForm.get('confirmPassword') as FormControl;
  }

  getErrorText(controlName: string): string {
    const control = this.registerForm.get(controlName) as FormControl;
    return this.getErrorMessage(control);
  }

  onCancelClick() {
    this.dialogRef.close(DialogResult.CLOSE);
  }

  private getErrorMessage(control: FormControl): string {
    let errorMessage = '';
    if (control.errors) {
      if (control.errors['required']) {
        errorMessage = 'Field is required';
      }
      if (control.errors['email']) {
        errorMessage = 'Incorrect email';
      }
      if (control.errors['minlength']) {
        errorMessage = 'Min length - 6 symbols';
      }

    }
    return errorMessage;
  }

  onSubmit() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }
    this.loading = true;
    this.userService.register(this.registerForm.value)
      .pipe(first())
      .subscribe(
        () => {
          // this.ngRedux.dispatch(loginUserAction(
          //   {login: this.registerForm.controls['login'].value,
          //     password: this.registerForm.controls['password'].value}
          //     ));
          this.notifier.notify('success', 'Registration successful. Check your email to activate your account!');
          this.onCancelClick();
        },
        error => {
          console.log(error);
          this.error = error;
          this.loading = false;
        });
  }

}


