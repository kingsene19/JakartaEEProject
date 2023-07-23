import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MarqueModelServer } from 'src/app/models/marque.model';
import { ProduitModelServer } from 'src/app/models/produit.model';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-products-marque',
  templateUrl: './products-marque.component.html',
  styleUrls: ['./products-marque.component.scss']
})

export class ProductsMarqueComponent implements OnInit {

  id!: number;
  nom!: string;
  errorMsg!: string;
  products!: ProduitModelServer[];

  constructor(private apiService: ApiService, private route: ActivatedRoute) {}

  loadProducts() {
    this.apiService.getMarqueProduits(this.id).subscribe(
      produits => {
        this.products = produits;
      },
      error => {
        this.errorMsg = "Une erreur est survenue pendant la récupération des données";
      }
    );
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe( params => {
      this.id = params['id'];
      this.nom = params['nom'].replace('-', ' ').toUpperCase();
    });
    this.loadProducts();
  }

}
