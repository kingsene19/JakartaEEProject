import { ProduitModelServer } from "./produit.model";

export interface MarqueModelServer {
    id: Number;
    nom: String;
    produitCollection?: ProduitModelServer[]
}