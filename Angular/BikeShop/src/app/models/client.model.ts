import { CommandeModelServer } from "./commande.model";

export interface ClientModelServer {
    id: number;
    prenom: String;
    nom: String;
    telephone: String;
    email: String;
    adresse: {
        adresse: String;
        ville: String;
        etat: String;
        codeZip: String
    }; 
    commandeCollection?: CommandeModelServer[]
}