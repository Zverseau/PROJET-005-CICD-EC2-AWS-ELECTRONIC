import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { SidebarComponent } from './corps/sidebar/sidebar.component';
import { TopbarComponent } from './corps/topbar/topbar.component';
import { BodyComponent } from './body/body.component';
import { CategorieComponent } from './categorie/categorie.component';
import { ModifierCategorieComponent } from './modifier-categorie/modifier-categorie.component';
import { ProduitComponent } from './produit/produit.component';
import { CommandeComponent } from './commande/commande.component';
import { HttpClientModule, provideHttpClient, withInterceptors} from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommandeDetailsComponent } from './commande-details/commande-details.component';
import { InscriptionComponent } from './auth/inscription/inscription.component';
import { ConnexionComponent } from './auth/connexion/connexion.component';
import { FormatMontantPipe } from './pipes/format-montant.pipe';
import { RouterModule, ROUTES } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { jwtInterceptor } from './interceptors/interceptor.interceptor';
@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    TopbarComponent,
    BodyComponent,
    CategorieComponent,
    ModifierCategorieComponent,
    ProduitComponent,
    CommandeComponent,
    CommandeDetailsComponent,
    InscriptionComponent,
    ConnexionComponent,
    FormatMontantPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule, 
  ],
  providers: [
    provideHttpClient(withInterceptors([jwtInterceptor]))
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
