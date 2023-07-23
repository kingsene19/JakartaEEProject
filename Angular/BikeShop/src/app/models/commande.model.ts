import { ArticleModelServer } from "./article.model";
import { EmployeModelServer } from "./employe.model";
import { MagasinModelServer } from "./magasin.model";

export interface CommandeModelServer {
    numero?: Number;
    statut: Number;
    dateCommande: string;
    dateLivraisonVoulue: string;
    dateLivraison?: Date;
    clientId: ClientDetailsModelServer;
    vendeurId: EmployeModelServer;
    magasinId?: MagasinModelServer;
    articleCommandeCollection: ArticleModelServer[];
}

export interface ClientDetailsModelServer {
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
}