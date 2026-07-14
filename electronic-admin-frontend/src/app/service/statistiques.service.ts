import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Statistiques } from '../model/statistiques';

@Injectable({
  providedIn: 'root'
})
export class StatistiquesService {

private apiUrl = `${environment.apiUrl}/api/v1/statistiques`;

  constructor(private http: HttpClient) { }

getStatistiques(): Observable<Statistiques> {
    return this.http.get<Statistiques>(`${this.apiUrl}/dashboard`);
  }
 
}
