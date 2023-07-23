import { ProduitModelServer } from "./produit.model";

export interface CategorieModelServer {
    id: Number;
    nom: String;
    produitCollection?: ProduitModelServer[]
}

