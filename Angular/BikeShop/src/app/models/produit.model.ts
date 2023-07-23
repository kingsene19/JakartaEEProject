import { ArticleModelServer } from "./article.model";
import { CategorieModelServer } from "./categorie.model";
import { MarqueModelServer } from "./marque.model";
import { StockModelServer } from "./stock.model";

export interface ProduitModelServer {
    id?: Number;
    nom: String;
    anneeModel: Number;
    prixDepart: number;
    categorieId: CategorieModelServer,
    marqueId: MarqueModelServer,
    stockCollection?: StockModelServer[],
    articleCommandeCollection?: ArticleModelServer[]
}

export interface ProduitModelInterfaceResponse {
    produit: ProduitModelServer;
    stocks: StockModelServer[]
}