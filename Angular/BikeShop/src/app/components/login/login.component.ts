import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent {

  userInfos = {
    client: {
      email: ''
    },
    password: ''
  }
  loginValid: boolean =true;

  constructor(private authService: AuthService) {}

  login() {
    this.loginValid = this.authService.login(this.userInfos);
  }
}
