import { Injectable } from '@angular/core';
import { UserModelServer } from '../models/user.model';
import { Router } from '@angular/router';
import { CartService } from './cart.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private isLoggedIn:boolean = false;
  private userData?: UserModelServer | null;
  private STORAGE_KEY = "users";

  constructor(private router: Router, private cartService: CartService) { }

  login(user: any): boolean {
    const index = this.getUsers().findIndex(result => (result.client.email === user.client.email) && (result.password === user.password));
    if (index !== -1) {
      this.isLoggedIn = true;
      this.userData = this.getUsers()[index];
      this.router.navigate(["/"]);
    }
    return this.isLoggedIn;
  }

  logout(): void {
    this.isLoggedIn = false;
    this.userData = null;
    this.cartService.clearCart()
    this.router.navigate(["/"]);
  }

  getIsLoggedIn(): boolean {
    return this.isLoggedIn;
  }

  getUserData(): any {
    return this.userData;
  }

  getUsers(): UserModelServer[] {
    const usersData = localStorage.getItem(this.STORAGE_KEY);
    return usersData ? JSON.parse(usersData) : [];
  }
}
