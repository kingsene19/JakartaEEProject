import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ProduitModelServer } from 'src/app/models/produit.model';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss']
})

export class ProductCardComponent {
  @Input() produit!: ProduitModelServer;
  constructor(private router: Router) {}

  redirectToDetailsPage() {
    const produitId = this.produit.id;
    this.router.navigate(['/produits', produitId], {queryParams: {produitId: produitId}});
  }

}
