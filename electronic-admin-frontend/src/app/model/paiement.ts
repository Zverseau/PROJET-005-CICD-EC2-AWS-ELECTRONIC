import { Commande } from "./commande";

export interface Paiement {
    id: number;
    montantTotal: number;
    referencePaiement: string;
    statutPaiement: string;
    commandeId: string;
    createdAt: string;
}