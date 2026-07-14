export interface Produit {
    id: number;
    nomProduit: string;
    description: string;
    stock: number;
    prixUnitaire: number;
    photoProduit: string;
    categorieId: number;
}