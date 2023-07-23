import { Component, OnInit } from '@angular/core';
import { CartModelServer, CartProduitServer } from 'src/app/models/cart.model';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})

export class CartComponent implements OnInit{

  subtotal!: number;
  addNotAuthorized: boolean = false;

  constructor(public cartService: CartService, public authService: AuthService, private userService: UsersService) {}

  ngOnInit(): void {
    
  }

  changeQuantity(item: CartProduitServer, increaseQuantity: Boolean) {
    if (increaseQuantity === true && item.numInCart + 1  > item.stock!.quantite ) {
      this.addNotAuthorized = true;
    } else if (increaseQuantity === false && item.numInCart <= item.stock!.quantite) {
      this.addNotAuthorized = false;
    } else {
      this.addNotAuthorized = false;
    }
    if (!this.addNotAuthorized) {
      this.cartService.updateItem(item, increaseQuantity);
      if (this.authService.getIsLoggedIn() === true ) {
        this.authService.getUserData().cart = this.cartService.getItems();
        this.userService.updateUser(this.authService.getUserData());
      }
    }
  }

  setIsLoggedIn():boolean {
    return this.authService.getIsLoggedIn();
  }

  getCart(): CartModelServer {
    if (this.setIsLoggedIn()) {
      return this.authService.getUserData().cart;
    } else {
      return this.cartService.getItems();
    }
  }

}
