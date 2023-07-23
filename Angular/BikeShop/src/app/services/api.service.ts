import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { CategorieModelServer } from '../models/categorie.model';
import { MarqueModelServer } from '../models/marque.model';
import { ProduitModelInterfaceResponse, ProduitModelServer } from '../models/produit.model';
import { CommandeModelServer } from '../models/commande.model';
import { ArticleModelServer } from '../models/article.model';
import { ClientModelServer } from '../models/client.model';
import { StockModelServer } from '../models/stock.model';

@Injectable({
  providedIn: 'root'
})


export class ApiService {

  private apiUrl = "http://localhost:8080/VentesVelos-1.0-SNAPSHOT/api";
  

  constructor(private http: HttpClient) { }

  getCategories(): Observable<CategorieModelServer[]> {
    const url = `${this.apiUrl}/categorie`;
    const headers = new HttpHeaders().set("Accept", "application/json");
    return this.http.get<CategorieModelServer[]>(url, {headers});
  }

  getMarques(): Observable<MarqueModelServer[]> {
    const url = `${this.apiUrl}/marque`;
    const headers = new HttpHeaders().set("Accept", "application/json");
    return this.http.get<MarqueModelServer[]>(url, {headers});
  }

  getProduits(): Observable<ProduitModelServer[]> {
    const url = `${this.apiUrl}/produit`;
    const headers = new HttpHeaders().set("Accept", "application/json");
    return this.http.get<ProduitModelServer[]>(url, {headers});
  }

  getProduit(id: number): Observable<ProduitModelInterfaceResponse> {
    const url = `${this.apiUrl}/produit/${id}`;
    const headers = new HttpHeaders().set("Accept", "application/json");
    return this.http.get<ProduitModelInterfaceResponse>(url, {headers});
  }

  getCategorieProduits(id: number): Observable<ProduitModelServer[]> {
    const url= `${this.apiUrl}/categorie/${id}`;
    console.log(url);
    const headers = new HttpHeaders().set("Accept", "application/json");
    return this.http.get<ProduitModelServer[]>(url, {headers});
  }

  getMarqueProduits(id: number): Observable<ProduitModelServer[]> {
    const url = `${this.apiUrl}/marque/${id}`;
    const headers = new HttpHeaders().set("Accept", "application/json");
    return this.http.get<ProduitModelServer[]>(url, {headers});
  }

  addCommande(commande: CommandeModelServer): Observable<CommandeModelServer> {
    const url = `${this.apiUrl}/commande`;
    const headers = new HttpHeaders().set("Content-Type", "application/json").set("Accept", "application/json");
    return this.http.put<CommandeModelServer>(url, commande, {headers});
  }

  addArticle(article: ArticleModelServer): Observable<ArticleModelServer> {
    const url = `${this.apiUrl}/article`;
    const headers = new HttpHeaders().set("Content-Type", "application/json").set("Accept", "application/json");
    return this.http.put<ArticleModelServer>(url, article, {headers});
  }

  addClient(client: ClientModelServer): Observable<ClientModelServer> {
    const url = `${this.apiUrl}/client`;
    const headers = new HttpHeaders().set("Content-Type", "application/json").set("Accept", "application/json");
    return this.http.put<ClientModelServer>(url, client, {headers});
  }

  getCommandesClient(id:number): Observable<CommandeModelServer[]> {
    const url = `${this.apiUrl}/client/${id}`;
    const headers = new HttpHeaders().set("Accept", "application/json");
    return this.http.get<CommandeModelServer[]>(url, {headers});
  }

  updateStock(stock:StockModelServer): Observable<StockModelServer> {
    const url = `${this.apiUrl}/stock`;
    const headers = new HttpHeaders().set("Content-Type", "application/json").set("Accept", "application/json");
    return this.http.put<StockModelServer>(url, stock, {headers});
  }

}
