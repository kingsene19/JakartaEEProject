package com.example.bikeshopmobile.utils;

import com.example.bikeshopmobile.entites.ArticleCommande;
import com.example.bikeshopmobile.entites.Categorie;
import com.example.bikeshopmobile.entites.Client;
import com.example.bikeshopmobile.entites.Commande;
import com.example.bikeshopmobile.entites.Employe;
import com.example.bikeshopmobile.entites.Localisation;
import com.example.bikeshopmobile.entites.Magasin;
import com.example.bikeshopmobile.entites.Marque;
import com.example.bikeshopmobile.entites.Produit;
import com.example.bikeshopmobile.entites.Reponse;
import com.example.bikeshopmobile.entites.Stock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    // Articles
    @Headers({
            "Accept: application/json"
    })
    @GET("article")
    Call<List<ArticleCommande>> getArticles();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("article")
    Call<ArticleCommande> addOrUpdateArticle(@Body ArticleCommande articleCommande);

    @Headers({
            "Accept: application/json"
    })
    @DELETE("article/{numero}/{ligne}")
    Call<Reponse> deleteArticle(@Path("numero") Integer numero, @Path("ligne") Integer ligne);

    // Categories
    @Headers({
            "Accept: application/json"
    })
    @GET("categorie")
    Call<List<Categorie>> getCategories();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("categorie")
    Call<Categorie> addOrUpdateCategorie(@Body Categorie categorie);

    @Headers({
            "Accept: application/json"
    })
    @DELETE("categorie/{id}")
    Call<Reponse> deleteCategorie(@Path("id") Integer id);

    //Clients
    @Headers({
            "Accept: application/json"
    })
    @GET("client")
    Call<List<Client>> getClients();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("client")
    Call<Client> addOrUpdateClient(@Body Client client);

    @Headers({
            "Accept: application/json"
    })
    @DELETE("client/{id}")
    Call<Reponse> deleteClient(@Path("id") Integer id);

    // Commandes
    @Headers({
            "Accept: application/json"
    })
    @GET("commande")
    Call<List<Commande>> getCommandes();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("commande")
    Call<Commande> addOrUpdateCommande(@Body Commande commande);

    @Headers({
            "Accept: application/json"
    })
    @DELETE("commande/{numero}")
    Call<Reponse> deleteCommande(@Path("numero") Integer numero);

    //Employes
    @Headers({
            "Accept: application/json"
    })
    @GET("employe")
    Call<List<Employe>> getEmployes();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("employe")
    Call<Employe> addOrUpdateEmploye(@Body Employe employe);

    @Headers({
            "Accept: application/json"
    })
    @DELETE("employe/{id}")
    Call<Reponse> deleteEmploye(@Path("id") Integer id);

    //Magasins
    @Headers({
            "Accept: application/json"
    })
    @GET("magasin")
    Call<List<Magasin>> getMagasins();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("magasin")
    Call<Magasin> addOrUpdateMagasin(@Body Magasin magasin);

    @Headers({
            "Accept: application/json"
    })
    @DELETE("magasin/{id}")
    Call<Reponse> deleteMagasin(@Path("id") Integer id);

    //Marques
    @Headers({
            "Accept: application/json"
    })
    @GET("marque")
    Call<List<Marque>> getMarques();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("marque")
    Call<Marque> addOrUpdateMarque(@Body Marque marque);

    @Headers({
            "Accept: application/json"
    })
    @DELETE("marque/{id}")
    Call<Reponse> deleteMarque(@Path("id") Integer id);

    //Produits
    @Headers({
            "Accept: application/json"
    })
    @GET("produit")
    Call<List<Produit>> getProduits();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("produit")
    Call<Produit> addOrUpdateProduit(@Body Produit produit);

    @Headers({
            "Accept: application/json"
    })
    @DELETE("produit/{id}")
    Call<Reponse> deleteProduit(@Path("id") Integer id);

    //Stocks
    @Headers({
            "Accept: application/json"
    })
    @GET("stock")
    Call<List<Stock>> getStocks();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("stock")
    Call<Stock> addOrUpdateStock(@Body Stock stock);

    @Headers({
            "Accept: application/json"
    })
    @DELETE("stock/{magasin_id}/{produit_id}")
    Call<Reponse> deleteStock(@Path("magasin_id") Integer magasin_id, @Path("produit_id") Integer produit_id);

    // Localisation
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("localisation")
    Call<Localisation> addOrUpdateLocalisation(@Body Localisation localisation);
}
