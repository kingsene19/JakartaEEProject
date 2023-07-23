import { Component, OnInit } from '@angular/core';
import { ProduitModelServer } from 'src/app/models/produit.model';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})

export class ProductsComponent implements OnInit {

  produits!: ProduitModelServer[];
  errorMsg!: string;

  constructor(private apiService: ApiService) {}

  loadProduits() {
    this.apiService.getProduits().subscribe(
      produits => {
      this.produits = produits;
      },
      error => {
        this.errorMsg = "Une erreur est survenue pendant la récupération des données";
      }
    );
  }

  ngOnInit() {
    this.loadProduits();
  }


}
