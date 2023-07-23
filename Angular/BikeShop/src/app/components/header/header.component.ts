import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faCartShopping, faHeart } from '@fortawesome/free-solid-svg-icons';
import { CartModelServer } from 'src/app/models/cart.model';
import { CategorieModelServer } from 'src/app/models/categorie.model';
import { ApiService } from 'src/app/services/api.service';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})

export class HeaderComponent implements OnInit {

  faCart = faCartShopping;
  faHeart = faHeart;

  cart!: CartModelServer;
  
  constructor(private cartService: CartService, private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    
  }

  redirectToLogin() {
    this.router.navigate(['/login']);
  }

  logout() {
    this.authService.logout();
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