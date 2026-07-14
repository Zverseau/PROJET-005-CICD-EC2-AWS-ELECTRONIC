import { Component, OnInit } from '@angular/core';
import { Categorie } from '../model/categorie';
import { CategorieService } from '../service/categorie.service';

@Component({
  selector: 'app-categorie',
  templateUrl: './categorie.component.html',
  styleUrls: ['./categorie.component.css']
})
export class CategorieComponent implements OnInit {

  categories: Categorie[] = [];

  categorie: Categorie = {
    id: 0,
    nomCategorie: ''
  };
    isEditing: boolean = false;
    
  // Pagination
  currentPage: number = 0;
  pageSize: number = 5; // Nombre d'éléments par page
  totalElements: number = 0;

  constructor(private categorieService: CategorieService) {}

  ngOnInit(): void {
    this.getAllCategories();
  }

  getAllCategories() {
    this.categorieService.getAllCategorie()
      .subscribe(data => {
        this.categories = data;
        this.totalElements = data.length;

      });
  }

  addCategorie() {
    if (!this.categorie.nomCategorie.trim()) return;

    this.categorieService.addCategorie(this.categorie)
      .subscribe(() => {
        this.categorie.nomCategorie = '';
        this.getAllCategories();
      });
  }


 updateCategorie() {
    if (!this.categorie.nomCategorie.trim() || this.categorie.id === 0) return;

    this.categorieService.updtCategorie(this.categorie.id.toString(), this.categorie)
      .subscribe(() => {
        this.resetForm();
        this.getAllCategories();
      });
  }

  editCategorie(cat: Categorie) {
    this.categorie = { ...cat };
    this.isEditing = true;
  }

  deleteCategorie(id: number) {
    this.categorieService.deleteCategorie(id.toString())
      .subscribe(() => {
        this.getAllCategories();
      });
  }

   resetForm() {
    this.categorie = {
      id: 0,
      nomCategorie: ''
    };
    this.isEditing = false;
  }

  onSubmit() {
    if (this.isEditing) {
      this.updateCategorie();
    } else {
      this.addCategorie();
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
    this.currentPage = 0; 
  }
}