import { Component } from '@angular/core';
import { MarqueModelServer } from 'src/app/models/marque.model';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-marques',
  templateUrl: './marques.component.html',
  styleUrls: ['./marques.component.scss']
})

export class MarquesComponent {
  marques!: MarqueModelServer[];
  errorMsg!: string;

  constructor(private apiService: ApiService) {}

  loadMarques() {
    this.apiService.getMarques().subscribe(
      marques => {
      this.marques = marques;
      },
      error => {
        this.errorMsg = "Une erreur est survenue pendant la récupération des données";
      }
    );
  }

  ngOnInit() {
    this.loadMarques();
  }
}
