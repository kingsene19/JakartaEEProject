import { Component, OnInit } from '@angular/core';
import { CategorieModelServer } from 'src/app/models/categorie.model';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit{

  categories!: CategorieModelServer[];
  errorMsg!: string;

  constructor(private apiService: ApiService) {}

  loadCategories() {
    this.apiService.getCategories().subscribe(
      categories => {
      this.categories = categories;
      },
      error => {
        this.errorMsg = "Une erreur est survenue pendant la récupération des données";
      }
    );
  }

  ngOnInit() {
    this.loadCategories();
  }
}
