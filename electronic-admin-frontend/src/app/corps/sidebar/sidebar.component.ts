import { Component } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  isCollapsed = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}


  toggleSidebar() {
    this.isCollapsed = !this.isCollapsed;
  }


  logout() {
    // Appelle l'API de déconnexion (optionnel, selon ton backend)
    this.authService.logout().subscribe({
      next: () => {
        this.authService.clearStorage();
        this.router.navigate(['/connexion']);
      },
      error: (err) => {
        console.error('Erreur lors de la déconnexion', err);
        // Même en cas d'erreur, on vide le storage et on redirige
        this.authService.clearStorage();
        this.router.navigate(['/connexion']);
      }
    });
  }
}