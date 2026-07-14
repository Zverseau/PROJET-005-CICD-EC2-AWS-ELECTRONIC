import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BodyComponent } from './body/body.component';
import { CommandeComponent } from './commande/commande.component';
import { CategorieComponent } from './categorie/categorie.component';
import { ProduitComponent } from './produit/produit.component';
import { ModifierCategorieComponent } from './modifier-categorie/modifier-categorie.component';
import { CommandeDetailsComponent } from './commande-details/commande-details.component';
import { InscriptionComponent } from './auth/inscription/inscription.component';
import { ConnexionComponent } from './auth/connexion/connexion.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: '/connexion', pathMatch: 'full' },

  { path: 'connexion', component: ConnexionComponent },
  { path: 'inscription', component: InscriptionComponent },

  { path: 'accueil', component: BodyComponent, canActivate: [AuthGuard] },
  { path: 'categorie', component: CategorieComponent, canActivate: [AuthGuard] },
  { path: 'modifier-categorie/:id', component: ModifierCategorieComponent, canActivate: [AuthGuard] },
  { path: 'produit', component: ProduitComponent, canActivate: [AuthGuard] },
  { path: 'commande', component: CommandeComponent, canActivate: [AuthGuard] },
  { path: 'commande-details/:id', component: CommandeDetailsComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
