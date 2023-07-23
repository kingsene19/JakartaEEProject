import { CommandeModelServer } from "./commande.model"
import { ProduitModelServer } from "./produit.model"

export interface ArticleModelServer {
    articleCommandePK: {
        numeroCommande: Number;
        ligne: Number
    };
    quantite: Number;
    prixDepart: Number;
    remise: Number;
    produitId: ProduitModelServer;
    commandeId: CommandeModelServer
}