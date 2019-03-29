import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DialogsComponent } from './dialogs.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { EnterEmailComponent } from './enter-email/enter-email.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MaterialModule} from '../../material';
import {ScrollingModule} from '@angular/cdk/scrolling';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    ScrollingModule
  ],
  declarations: [DialogsComponent, SignInComponent,
    SignUpComponent, EnterEmailComponent],
  exports: [DialogsComponent, SignInComponent,
    SignUpComponent, EnterEmailComponent],
  entryComponents: [DialogsComponent, SignInComponent,
    SignUpComponent, EnterEmailComponent]
})
export class DialogsModule { }
