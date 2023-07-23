import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProduitModelInterfaceResponse, ProduitModelServer } from 'src/app/models/produit.model';
import { StockModelServer } from 'src/app/models/stock.model';
import { ApiService } from 'src/app/services/api.service';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { UsersService } from 'src/app/services/users.service';

@Component({
  selector: 'app-products-details',
  templateUrl: './products-details.component.html',
  styleUrls: ['./products-details.component.scss']
})

export class ProductsDetailsComponent implements OnInit{

  produit!: ProduitModelInterfaceResponse;
  selectedStock!: StockModelServer | null;
  produitId!: number;
  errorMsg!: string;
  quantite: number = 1;
  addNotAuthorized: boolean = false;

  constructor(private route: ActivatedRoute, private apiService: ApiService, private cartService: CartService, private authService: AuthService, private userService: UsersService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.produitId = params['produitId'];
    });
    this.loadProduit();
  }

  loadProduit() {
    this.apiService.getProduit(this.produitId).subscribe(
      produit => {
        this.produit = produit;
      },
      error => {
        this.errorMsg = "Une erreur s'est produite lors de la récupération des produits";
      }
    )
  }

  selectStock(stock: any) {
    if (this.selectedStock == null ) {
      this.selectedStock = stock;
    } else {
      this.selectedStock = null;
    }
  }

  onSelectionChange(event: any) {
    this.quantite = event.target.value;
  }

  addToCart(produit: ProduitModelServer) {
    if (this.quantite > this.selectedStock!.quantite ) {
      this.addNotAuthorized = true;
    } else {
      this.addNotAuthorized = false;
      this.cartService.updateItem({
        product: produit,
        numInCart: this.quantite,
        stock: (this.selectedStock?this.selectedStock:undefined)
      }, true);
      if (this.authService.getIsLoggedIn() === true ) {
        this.authService.getUserData().cart = this.cartService.getItems();
        this.userService.updateUser(this.authService.getUserData());
      }
    }
  }
}
