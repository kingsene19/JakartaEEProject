import { Component, OnInit } from '@angular/core';
import { ClientModelServer } from 'src/app/models/client.model';
import { UserModelServer } from 'src/app/models/user.model';
import { ApiService } from 'src/app/services/api.service';
import { AuthService } from 'src/app/services/auth.service';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit{

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
  userModified!: boolean;

  constructor(private authService: AuthService, private userService: UsersService) {}

  ngOnInit() {
    this.user = this.authService.getUserData();
  }

  passwordsMatchValidator() {
    const password = this.user.password;
    const confirmPassword = this.confirmPwd;

    return password === confirmPassword ? true : false;
  }

  modifyUser() {
    if (this.passwordsMatchValidator()) {
      this.userService.updateUser(this.user);
      this.pwdValidate = true;
      this.userModified=true;
      this.user = this.authService.getUserData();
    } else {
      this.pwdValidate = false;
      this.userModified=false;
    }
  }

  deleteUser() {
    this.userService.deleteUser(this.user);
    this.authService.logout();
  }

}
