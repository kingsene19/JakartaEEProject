import { Injectable } from '@angular/core';
import { CartModelServer, CartProduitServer } from '../models/cart.model';

@Injectable({
  providedIn: 'root'
})

export class CartService {
  private cartItems: CartModelServer = {
    total: 0,
    itemsInCart: 0,
    data: []
  };

  constructor() { }

  getItems(): CartModelServer{
    return this.cartItems;
  }

  addItem(item: CartProduitServer): void {
    this.cartItems.data.push(item);
    this.cartItems.itemsInCart += 1;
    this.calculateTotal();
  }

  removeItem(item: CartProduitServer): void {
    const {available, index} = this.checkAvailability(item);
    if (available) {
      this.cartItems.data.splice(index, 1);
    }
    this.cartItems.itemsInCart  -= 1;
    this.calculateTotal();
  }

  updateItem(item: CartProduitServer, increaseQuantity: Boolean, quantite?: number): void {
    const {available, index} = this.checkAvailability(item);
    if (quantite) {
      if (available) {
        if (increaseQuantity) {
          this.cartItems.data[index].numInCart += quantite;
        } else {
          this.cartItems.data[index].numInCart -= quantite;
          if (this.cartItems.data[index].numInCart === 0)
            this.removeItem(item);
        } 
      } else {
        this.addItem(item);
      }
    } else {
      if (available) {
        if (increaseQuantity) {
          this.cartItems.data[index].numInCart += 1;
        } else {
          this.cartItems.data[index].numInCart -= 1;
          if (this.cartItems.data[index].numInCart === 0)
            this.removeItem(item);
        } 
      } else {
        this.addItem(item);
      }
    }
    this.calculateTotal();
  }

  clearCart(): void {
    this.cartItems = {
      total: 0,
      itemsInCart: 0,
      data: []
    };
  }

  calculateTotal(): void {
    this.cartItems.total = 0;
    for (const item of this.cartItems.data) {
      this.cartItems.total += item.product.prixDepart * item.numInCart;
    }
  }

  calculateSubTotal(item: CartProduitServer): number {
    return item.product.prixDepart * item.numInCart;
  }

  checkAvailability(item: CartProduitServer): {available: boolean, index: number} {
    const index = this.cartItems.data.findIndex(cartItem => item.product === cartItem.product && cartItem.stock?.magasinId === item.stock?.magasinId);
    const available = index !== -1;
    return { available, index };
  }
}
