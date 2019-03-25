import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DialogsComponent } from './dialogs.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { EnterEmailComponent } from './enter-email/enter-email.component';

@NgModule({
  declarations: [DialogsComponent, SignInComponent, SignUpComponent, EnterEmailComponent],
  imports: [
    CommonModule
  ]
})
export class DialogsModule { }
