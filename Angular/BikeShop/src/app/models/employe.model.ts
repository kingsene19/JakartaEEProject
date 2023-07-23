import { CommandeModelServer } from "./commande.model";
import { MagasinModelServer } from "./magasin.model";

export interface EmployeModelServer {
    id: Number;
    prenom: String;
    nom: String;
    telephone?: String;
    email: String;
    adresse?: {
        adresse: String;
        ville: String;
        etat: String;
        codeZip: String
    };
    actif: Number;
    managerId?: EmployeModelServer;
    magasinId: MagasinModelServer;
    employeCollection?: EmployeModelServer[];
    commandeCollection?: CommandeModelServer[]
}