import { MagasinModelServer } from "./magasin.model"
import { ProduitModelServer } from "./produit.model";

export interface StockModelServer {
    stockPK: {
        magasinId: MagasinModelServer;
        produitId: ProduitModelServer;
    };
    quantite: number;
    produitId: ProduitModelServer;
    magasinId: MagasinModelServer;
}