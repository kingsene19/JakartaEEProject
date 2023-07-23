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
  private CLIENT_STORAGE = "num_client";
  private users = this.getUsers();

  constructor(private apiService: ApiService, private cartService: CartService, private authService: AuthService) { }

  saveUser(user: UserModelServer): void {
    localStorage.setItem(this.COMMANDE_STORAGE, JSON.stringify(1616));
    localStorage.setItem(this.CLIENT_STORAGE, JSON.stringify(1458));
    user.client.id = this.getClientNumeroStart();
    user.client.commandeCollection = [];
    user.cart = this.cartService.getItems();
    this.apiService.addClient(user.client).subscribe(
      result => {
        this.users.push(user);
        localStorage.setItem(this.STORAGE_KEY, JSON.stringify(this.users));
        this.authService.login(user);
      },
      error => {
        console.log(`Une erreur s'est produite: ${error}`);
      }
    )
    this.setClientNumeroStart();
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

  getClientNumeroStart() {
    const clientStart = localStorage.getItem(this.CLIENT_STORAGE);
    return clientStart ? JSON.parse(clientStart) : 1458;
  }

  setClientNumeroStart() {
    localStorage.setItem(this.CLIENT_STORAGE, JSON.stringify(this.getClientNumeroStart() + 1));
  }

}
