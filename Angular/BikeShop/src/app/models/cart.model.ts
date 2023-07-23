import { ProduitModelServer } from "./produit.model";
import { StockModelServer } from "./stock.model";

export interface CartModelServer {
    total: number;
    itemsInCart: number;
    data: CartProduitServer[]
}

export interface CartProduitServer {
  product: ProduitModelServer;
  numInCart: number;
  stock?: StockModelServer
}