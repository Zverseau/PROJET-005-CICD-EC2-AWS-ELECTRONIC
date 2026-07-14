import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  const token = localStorage.getItem('auth_token');
  console.log("TOKEN ENVOYE :", token);

  // Routes publiques
  const publicRoutes = [
    '/api/v1/authentification/login',
    '/api/v1/authentification/register',
    '/api/v1/produits/getAll',
    '/api/v1/produits/getById',
    '/api/v1/categories/getAll',
    '/api/v1/commandes/commander',
    '/api/v1/authentification/logout'
  ];

  const isPublicRoute = publicRoutes.some(route => req.url.includes(route));

  let authReq = req;

  if (!isPublicRoute && token) {
    // Cloner la requête
    authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
        // ⚠️ N'ajoute Content-Type que si ce n'est pas du FormData
        ...(req.body instanceof FormData ? {} : { 'Content-Type': 'application/json' })
      }
    });
    console.log('✅ Token ajouté à la requête:', req.url);
  } else {
    console.log('🌐 Route publique ou sans token:', req.url);
  }

  return next(authReq).pipe(
    catchError(error => {
      if (error.status === 401) {
        console.log('❌ Token expiré ou invalide');
        localStorage.removeItem('auth_token');
        router.navigate(['/connexion']);
      } else if (error.status === 403) {
        console.log('❌ Accès interdit - Rôle insuffisant');
      }
      return throwError(() => error);
    })
  );
};