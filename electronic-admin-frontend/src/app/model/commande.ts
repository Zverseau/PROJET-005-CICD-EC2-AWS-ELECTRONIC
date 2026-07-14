import { LigneCommande } from "./ligneCommande";
import { Produit } from "./produit";

export interface Commande {
    id: number;
    nomClient: string;
    prenomClient: string;
    adresseClient: string;
    telephoneClient: string;
    statutCommande: string;
    lignesCommande: LigneCommande[];
    produits: Produit[];
}