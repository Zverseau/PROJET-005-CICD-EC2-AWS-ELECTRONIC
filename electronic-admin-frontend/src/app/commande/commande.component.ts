import { Component, OnInit } from '@angular/core';
import { Commande } from '../model/commande';
import { CommandeService } from '../service/commande.service';
import { LigneCommande } from '../model/ligneCommande';
import { Router } from '@angular/router';

@Component({
  selector: 'app-commande',
  templateUrl: './commande.component.html',
  styleUrl: './commande.component.css'
})
export class CommandeComponent implements OnInit {
  commandes: Commande[] = [];
  loading: boolean = false;
  error: string | null = null;
  
  statutOptions = [
    { value: 'EXPEDIEE', label: 'Expédiée', class: 'bg-secondary' },
    
  ];

    // Pagination
  currentPage: number = 0;
  pageSize: number = 5; // Nombre d'éléments par page
  totalElements: number = 0;

  constructor(private commandeService: CommandeService, private router: Router) { }

  ngOnInit(): void {
    this.loadCommandes();
  }

  loadCommandes(): void {
    this.loading = true;
    this.error = null;
    
    this.commandeService.getAllCommandes().subscribe({
      next: (data: Commande[]) => {
        this.commandes = data;
        this.loading = false;
        this.totalElements = data.length;
        console.log('Commandes chargées:', this.commandes);
      },
      error: (err) => {
        console.error('Erreur lors du chargement des commandes', err);
        this.error = 'Impossible de charger les commandes';
        this.loading = false;
      }
    });
  }

  getStatutClass(statut: string): string {

    if (statut === 'EN_ATTENTE') {
      return 'bg-warning';  
    }

    if (statut === 'EXPEDIEE') {
      return 'bg-success';
    }

    return 'bg-secondary';
  }

  getNombreArticles(commande: Commande): number {
    // Vérifiez que lignesCommande existe et est un tableau
    if (commande.lignesCommande && Array.isArray(commande.lignesCommande)) {
      return commande.lignesCommande.reduce((total: number, ligne: LigneCommande) => total + ligne.quantiteAchete, 0);
    }
    return 0;
  }

  getNomComplet(commande: Commande): string {
    return `${commande.prenomClient || ''} ${commande.nomClient || ''}`.trim().toUpperCase() || 'Client inconnu';
  }

  modifierStatut(commande: Commande, nouveauStatut: string): void {
    if (!confirm(`Voulez-vous changer le statut de la commande en "${nouveauStatut}" ?`)) {
      return;
    }
    
    this.commandeService.updateStatutCommande(commande.id, nouveauStatut).subscribe({
      next: (commandeModifiee: Commande) => {
        const index = this.commandes.findIndex(c => c.id === commande.id);
        if (index !== -1) {
          this.commandes[index] = commandeModifiee;
        }
        alert('Statut modifié avec succès');
      },
      error: (err) => {
        console.error('Erreur lors de la modification du statut', err);
        alert('Erreur lors de la modification du statut');
      }
    });
  }

    voirDetails(id: number) {
    this.router.navigate(['/commande-details', id]);
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