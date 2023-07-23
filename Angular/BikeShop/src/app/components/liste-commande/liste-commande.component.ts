import { Component, OnInit } from '@angular/core';
import { ClientModelServer } from 'src/app/models/client.model';
import { CommandeModelServer } from 'src/app/models/commande.model';
import { ApiService } from 'src/app/services/api.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-liste-commande',
  templateUrl: './liste-commande.component.html',
  styleUrls: ['./liste-commande.component.scss']
})
export class ListeCommandeComponent implements OnInit{

  client!: ClientModelServer;
  commandes!: CommandeModelServer[];

  constructor(private apiService: ApiService, private authService: AuthService) {}

  ngOnInit(): void {
    this.client = this.authService.getUserData().client;
    console.log(this.client);
    this.commandes = this.client.commandeCollection!;
  }

}
