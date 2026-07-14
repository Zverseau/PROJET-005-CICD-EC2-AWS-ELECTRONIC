import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { Commande } from '../model/commande';

@Injectable({
  providedIn: 'root'
})
export class CommandeService {

private apiUrl = `${environment.apiUrl}/api/v1/commandes`;

  constructor(private http: HttpClient) { }

  getAllCommandes(): Observable<any[]> {
      return this.http.get<any[]>(`${this.apiUrl}/getAll`);
    }
  getCommandesAttente(): Observable<Commande[]> {
      return this.http.get<Commande[]>(`${this.apiUrl}/en-attente`);
    }
  
  getCommandeById(id: string): Observable<any> {
      return this.http.get<any>(`${this.apiUrl}/getById/${id}`);
    }
  
  createCommande(formData: FormData): Observable<any> {
      return this.http.post<any>(`${this.apiUrl}/commander`, formData);
    }

  updateStatutCommande(id: number, statut: string): Observable<Commande> {
  const formData = new FormData();
  formData.append('statut', statut);
  return this.http.put<Commande>(`${this.apiUrl}/${id}`, formData); }
  
}
