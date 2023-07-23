import { CommandeModelServer } from "./commande.model";
import { EmployeModelServer } from "./employe.model";
import { StockModelServer } from "./stock.model";

export interface MagasinModelServer {
    id: Number;
    nom: String;
    telephone?: String;
    email: String;
    adresse: {
        adresse: String;
        ville: String;
        etat: String;
        codeZip: String;
    };
    employeCollection?: EmployeModelServer[];
    commandeCollection?: CommandeModelServer[];
    stockCollection?: StockModelServer[];
}