import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { UserModelServer } from '../models/user.model';
import { CartService } from './cart.service';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})

export class UsersService {

  private STORAGE_KEY = "users";
  private COMMANDE_STORAGE = "num_commande";
  private users = this.getUsers();

  constructor(private apiService: ApiService, private cartService: CartService, private authService: AuthService) { }

  saveUser(user: UserModelServer): void {
    user.client.id = null;
    user.client.commandeCollection = [];
    user.cart = this.cartService.getItems();
    this.apiService.addClient(user.client).subscribe(
      result => {
        user.client.id = result.id;
        this.users.push(user);
        localStorage.setItem(this.STORAGE_KEY, JSON.stringify(this.users));
        this.authService.login(user);
      },
      error => {
        console.log(`Une erreur s'est produite: ${error}`);
      }
    );
  }

  updateUser(user: UserModelServer):void {
    this.apiService.addClient(user.client).subscribe(
      result => {
        this.deleteUser(user);
        this.users.push(user);
        localStorage.setItem(this.STORAGE_KEY, JSON.stringify(this.users));
      }, 
      error => {
        console.log(`Une erreur s'est produit: ${error}`);
      }
    );
  }

  deleteUser(user: UserModelServer): void {
    const index = this.users.indexOf(user);
    this.users.splice(index);
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(this.users));
  }

  getUsers(): UserModelServer[] {
    const usersData = localStorage.getItem(this.STORAGE_KEY);
    return usersData ? JSON.parse(usersData) : [];
  }

  getCommandeNumeroStart() {
    const commandeStart = localStorage.getItem(this.COMMANDE_STORAGE);
    return commandeStart ? JSON.parse(commandeStart) : 1616;
  }

  setCommandeNumeroStart() {
    localStorage.setItem(this.COMMANDE_STORAGE, JSON.stringify(this.getCommandeNumeroStart() + 1));
  }

}
