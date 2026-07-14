import { Component, OnInit } from '@angular/core';
import { ProduitService } from '../service/produit.service';
import { CategorieService } from '../service/categorie.service';
import { Categorie } from '../model/categorie';
import { environment } from '../../environment/environment';

@Component({
  selector: 'app-produit',
  templateUrl: './produit.component.html',
  styleUrl: './produit.component.css'
})
export class ProduitComponent implements OnInit {

  imageBaseUrl = environment.apiUrl + '/api/v1/produits/uploads/produits/';
  produits: any[] = [];
  categories: Categorie[] = [];
  
  produit: any = {
    id: '',
    nomProduit: '',
    description: '',
    stock: 0,
    prixUnitaire: 0,
    photoProduit: '',
    categorieId: ''
  };

  selectedFile: File | null = null;
  isEditing: boolean = false;

    // Pagination
  currentPage: number = 0;
  pageSize: number = 5; // Nombre d'éléments par page
  totalElements: number = 0;

  constructor(
    private produitService: ProduitService,
    private categorieService: CategorieService
  ) {}

  ngOnInit(): void {
    this.getAllProduits();
    this.getAllCategories();
  }

  getAllProduits() {
    this.produitService.getAllProduits()
      .subscribe(data => {
        this.produits = data;
                this.totalElements = data.length;

      });
  }

  getAllCategories() {
    this.categorieService.getAllCategorie()
      .subscribe(data => {
        this.categories = data;
      });
  }
  // Ajoutez cette méthode dans ProduitComponent
getCategorieName(categorieId: string): string {
  const categorie = this.categories.find(c => c.id.toString() === categorieId);
  return categorie ? categorie.nomCategorie : 'Non catégorisé';
}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  createProduit() {
    const formData = new FormData();
    
    const produitData = {
      nomProduit: this.produit.nomProduit,
      description: this.produit.description,
      stock: this.produit.stock,
      prixUnitaire: this.produit.prixUnitaire,
      categorieId: this.produit.categorieId
    };

    formData.append('produit', new Blob([JSON.stringify(produitData)], { type: 'application/json' }));
    
    if (this.selectedFile) {
      formData.append('image', this.selectedFile);
    }

    this.produitService.createProduit(formData)
      .subscribe(() => {
        this.resetForm();
        this.getAllProduits();
      });
  }

  updateProduit() {
    const formData = new FormData();
    
    const produitData = {
      nomProduit: this.produit.nomProduit,
      description: this.produit.description,
      stock: this.produit.stock,
      prixUnitaire: this.produit.prixUnitaire,
      categorieId: this.produit.categorieId
    };

    formData.append('produit', new Blob([JSON.stringify(produitData)], { type: 'application/json' }));
    
    if (this.selectedFile) {
      formData.append('image', this.selectedFile);
    }

    this.produitService.updateProduit(this.produit.id, formData)
      .subscribe(() => {
        this.resetForm();
        this.getAllProduits();
      });
  }

  editProduit(produit: any) {
    this.produit = { ...produit };
    this.isEditing = true;
  }

  deleteProduit(id: string) {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce produit ?')) {
      this.produitService.deleteProduit(id)
        .subscribe(() => {
          this.getAllProduits();
        });
    }
  }

  resetForm() {
    this.produit = {
      id: '',
      nomProduit: '',
      description: '',
      stock: 0,
      prixUnitaire: 0,
      photoProduit: '',
      categorieId: ''
    };
    this.selectedFile = null;
    this.isEditing = false;
    
    const fileInput = document.getElementById('photoProduit') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  }

  onSubmit() {
    if (this.isEditing) {
      this.updateProduit();
    } else {
      this.createProduit();
    }
  }

  
  // --- LOGIQUE DE PAGINATION ---

  getTotalPages(): number {
    return Math.ceil(this.totalElements / this.pageSize);
  }

  getPages(): number[] {
    const totalPages = this.getTotalPages();
    const pages: number[] = [];
    for (let i = 0; i < totalPages; i++) {
      pages.push(i);
    }
    return pages;
  }

  getStartIndex(): number {
    return this.totalElements === 0 ? 0 : (this.currentPage * this.pageSize) + 1;
  }

  getEndIndex(): number {
    return Math.min((this.currentPage + 1) * this.pageSize, this.totalElements);
  }

  nextPage(): void {
    if (this.currentPage < this.getTotalPages() - 1) {
      this.currentPage++;
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
    }
  }

  goToPage(page: number): void {
    this.currentPage = page;
  }

  goToLastPage(): void {
    this.currentPage = this.getTotalPages() - 1;
  }

  onPageSizeChange(): void {
    this.currentPage = 0; // Retour à la première page si on change le nombre d'éléments
  }
}