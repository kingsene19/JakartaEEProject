import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserModelServer } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  user: UserModelServer = {
    client: {
      id: 0,
      email: '',
      prenom: '',
      nom: '',
      telephone: '',
      adresse: {
        adresse: '',
        ville: '',
        etat: '',
        codeZip: ''
      },
    },
    cart: {
      total: 0,
      itemsInCart: 0,
      data: []
    },
    password: ''
  };
  confirmPwd!: string;
  pwdValidate!: boolean;
  emailValide!: boolean;

  constructor(private userService: UsersService, private authService: AuthService) {}
  
  passwordsMatchValidator() {
    const password = this.user.password;
    const confirmPassword = this.confirmPwd;

    return password === confirmPassword ? true : false;
  }

  emailDoesNotExists() {
    const index = this.userService.getUsers().findIndex(user => user.client.email === this.user.client.email);
    return index !==-1 ? false: true;
  }

  ajoutUser() {
    if (this.passwordsMatchValidator() && this.emailDoesNotExists()) {
      this.pwdValidate = true;
      this.emailValide = true;
      this.userService.saveUser(this.user);
    } else if (!this.emailDoesNotExists()) {
      this.emailValide = false;
    } else if (this.passwordsMatchValidator() === false) {
      this.pwdValidate = false;
    }
  }
}
