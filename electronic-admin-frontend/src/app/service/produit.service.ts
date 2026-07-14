import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class ProduitService {
  private apiUrl = `${environment.apiUrl}/v1/produits`;

  constructor(private http: HttpClient) { }

  getAllProduits(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/getAll`);
  }

  getProduitById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/getById/${id}`);
  }

  createProduit(formData: FormData): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/create`, formData);
  }

  updateProduit(id: string, formData: FormData): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/update/${id}`, formData);
  }

  deleteProduit(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }
}
