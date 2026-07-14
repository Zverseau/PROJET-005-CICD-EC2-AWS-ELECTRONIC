import { Commande } from "./commande";
import { Produit } from "./produit";

export interface LigneCommande {
    produit: Produit;
    quantiteAchete: number;
    sousTotal: number;
} 