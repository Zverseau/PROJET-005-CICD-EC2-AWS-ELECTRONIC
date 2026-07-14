import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AdminRegisterRequest, LoginRequest, LoginResponse } from '../model/auth';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
   private apiUrl = `${environment.apiUrl}/api/v1/authentification`;
  private tokenKey = 'auth_token';
  //private adminIdKey = 'admin_id';

  constructor(private http: HttpClient) {}

login(credentials: LoginRequest): Observable<LoginResponse> {
  return this.http.post<LoginResponse>(`${this.apiUrl}/login/admin`, credentials)
    .pipe(
      tap(response => {
        console.log("TOKEN RECUPERE :", response.token);  // doit afficher le JWT
        this.setToken(response.token);
      })
    );
}

setToken(token: string): void {
  if (token) {
    localStorage.setItem(this.tokenKey, token);
  }
}

  register(adminData: AdminRegisterRequest): Observable<string> {
    return this.http.post(`${this.apiUrl}/register/admin`, adminData, {
      responseType: 'text'
    });
  }

  logout(): Observable<any> {
    return this.http.post(`${this.apiUrl}/logout`, {});
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  clearStorage(): void {
    localStorage.removeItem(this.tokenKey);
   // localStorage.removeItem(this.adminIdKey);
  }

  isAuthenticated(): boolean { 
    return !!this.getToken();
  }
}