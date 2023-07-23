import { Component, OnInit } from '@angular/core';
import { CartModelServer } from 'src/app/models/cart.model';
import { ClientModelServer } from 'src/app/models/client.model';
import { ClientDetailsModelServer, CommandeModelServer } from 'src/app/models/commande.model';
import { ApiService } from 'src/app/services/api.service';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { UsersService } from 'src/app/services/users.service';
import { ObservableInput, forkJoin } from 'rxjs';
import { ArticleModelServer } from 'src/app/models/article.model';


@Component({
  selector: 'app-commandes',
  templateUrl: './commandes.component.html',
  styleUrls: ['./commandes.component.scss']
})

export class CommandesComponent implements OnInit {

  cart!: CartModelServer;
  commande: CommandeModelServer = {
    statut: 0,
    dateCommande: '',
    dateLivraisonVoulue: '',
    clientId: {
      id: 0,
      prenom: "",
      nom: "",
      telephone: "",
      email: "",
      adresse: {
        adresse: "",
        ville: "",
        etat: "",
        codeZip: ""
      }
    },
    vendeurId: {
      id: 0,
      prenom: "",
      nom: "",
      email: "",
      actif: 0,
      magasinId: {
        id: 0,
        nom: "",
        email: "",
        adresse: {
          adresse: "",
          ville: "",
          etat: "",
          codeZip: ""
        }
      }
    },
    magasinId: {
      id: 0,
        nom: "",
        email: "",
        adresse: {
          adresse: "",
          ville: "",
          etat: "",
          codeZip: ""
        }
    },
    articleCommandeCollection: []
  };
  clientId!: ClientModelServer;
  clientDetails!: ClientDetailsModelServer;
  dateLivraisonVoulue!: Date;
  orderRegistered: boolean = false;
  articlesCommande: ArticleModelServer[] = [];


  constructor(
    private authService: AuthService,
    private apiService: ApiService,
    private cartService: CartService,
    private userService: UsersService,
  ) {}

  ngOnInit(): void {
    this.cart = this.authService.getUserData().cart;
    this.clientId = this.authService.getUserData().client;
    this.clientDetails = {
      id: this.clientId.id,
      email: this.clientId.email,
      nom: this.clientId.nom,
      prenom: this.clientId.prenom,
      telephone: this.clientId.telephone,
      adresse: this.clientId.adresse
    }
  }

  ajoutCommande() {
    this.setCommande();
    this.apiService.addCommande(this.commande).subscribe(
      result => {
        console.log("Commande added successfully");
        const addArticleObservables = [];
        for (let i = 1; i < this.cart.itemsInCart + 1; i++) {
          const a = {
            articleCommandePK: {
              ligne: i,
              numeroCommande: this.userService.getCommandeNumeroStart(),
            },
            quantite: this.cart.data[i - 1].numInCart,
            prixDepart: this.cart.data[i - 1].product.prixDepart,
            remise: 0,
            produitId: this.cart.data[i - 1].product,
            commandeId: this.commande,
          };
          this.articlesCommande.push(a);
          const addArticleObservable = this.apiService.addArticle(a);
          addArticleObservables.push(addArticleObservable);
        }
        forkJoin(addArticleObservables).subscribe(
          () => {
            console.log('All articles added successfully');
            const updateStockObservables = [];
            for (let i = 1; i < this.cart.itemsInCart + 1; i++) {
              const stock = this.cart.data[i - 1].stock!;
              stock.quantite -= this.cart.data[i - 1].numInCart;
              const updateStockObservable = this.apiService.updateStock(stock);
              updateStockObservables.push(updateStockObservable);
            }
            forkJoin(updateStockObservables).subscribe(
              () => {
                console.log('Stock updated successfully');
                this.resetUserInfos();
              },
              error => {
                console.log(error);
              }
            );
          },
          error => {
            console.log(error);
          }
        );
      },
      error => {
        console.log(error);
      }
    );
  }
  

  resetUserInfos() {
    console.log("Here");
    this.cartService.clearCart();
    this.authService.getUserData().cart = this.cartService.getItems();
    this.commande.articleCommandeCollection = this.articlesCommande;
    this.authService.getUserData().client.commandeCollection.push(this.commande);
    this.userService.updateUser(this.authService.getUserData());
    this.userService.setCommandeNumeroStart();
    this.orderRegistered = true;
    console.log(this.commande);
  }

  setCommande() {
    this.commande.numero = this.userService.getCommandeNumeroStart();
    this.commande.vendeurId = {
      id: 1449,
      prenom: 'Virgie',
      nom: 'Wiggins',
      actif: 1,
      email: 'virgie.wiggins@bikes.shop',
      magasinId: {
        id: 1,
        nom: 'Santa Cruz Bikes',
        telephone: '(831) 476-4321',
        email: 'santacruz@bikes.shop',
        adresse: {
          adresse: '3700 Portola Drive',
          ville: 'Santa Cruz',
          etat:'CA',
          codeZip: '95060'
        }
      }
    };
    this.commande.clientId = this.clientDetails;
    this.commande.magasinId = {
      id: 1,
      nom: 'Santa Cruz Bikes',
      telephone: '(831) 476-4321',
      email: 'santacruz@bikes.shop',
      adresse: {
        adresse: '3700 Portola Drive',
        ville: 'Santa Cruz',
        etat:'CA',
        codeZip: '95060'
      }
    },
    this.commande.statut = 1;
    this.commande.dateCommande = new Date().toJSON().slice(0, 10);
    this.commande.dateLivraisonVoulue = this.dateLivraisonVoulue.toString();
  }
}