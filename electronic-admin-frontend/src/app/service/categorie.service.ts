import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Categorie } from '../model/categorie';
import { Observable } from 'rxjs';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class CategorieService {

  private apiUrl = `${environment.apiUrl}/v1/categories`;

  constructor(private http: HttpClient) {}

  addCategorie(categorie: Categorie): Observable<Categorie>{
  return this.http.post<Categorie>(
    `${this.apiUrl}/create`,
    categorie
  );
}

updtCategorie(id: string, categorie: Categorie): Observable<Categorie>{
  return this.http.put<Categorie>(
    `${this.apiUrl}/update/${id}`,
    categorie
  );
}

getCategorieById(id: string): Observable<Categorie>{
  return this.http.get<Categorie>(
    `${this.apiUrl}/getById/${id}`
  );
}

getAllCategorie(): Observable<Categorie[]>{
  return this.http.get<Categorie[]>(
    `${this.apiUrl}/getAll`
  );
}

deleteCategorie(id: string): Observable<Categorie>{
  return this.http.delete<Categorie>(
    `${this.apiUrl}/delete/${id}`
  );
}
}
