import { Component, OnInit } from '@angular/core';
import { CommandeService } from '../service/commande.service';
import { Commande } from '../model/commande';
import { Router } from '@angular/router';
import { Statistiques } from '../model/statistiques';
import { StatistiquesService } from '../service/statistiques.service';

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrl: './body.component.css'
})
export class BodyComponent implements OnInit {

  commandes: Commande[] = [];
  stats!: Statistiques;
  statutOptions = [
    { value: 'EXPEDIEE', label: 'Expédiée', class: 'bg-secondary' },
    
  ];

  // Pagination
  currentPage: number = 0;
  pageSize: number = 5;  
  totalElements: number = 0;

  constructor(
    private commandeService: CommandeService,
    private router: Router,
    private statistiquesService: StatistiquesService,
) {}

  ngOnInit(): void {
    this.loadCommandesAttente();
    this.loadStatistiques();
}

loadStatistiques(): void {
  this.statistiquesService.getStatistiques().subscribe({
    next: (data) => {
      this.stats = data;
    },
    error: (err) => console.error(err)
  });
}

  loadCommandesAttente(): void {
    this.commandeService.getCommandesAttente().subscribe({
      next: (data) => {
        this.commandes = data;
        this.totalElements = data.length;
      },
      error: (err) => console.error('Erreur chargement commandes', err)
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

  voirDetails(id: number) {
    this.router.navigate(['/commande-details', id]);
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
