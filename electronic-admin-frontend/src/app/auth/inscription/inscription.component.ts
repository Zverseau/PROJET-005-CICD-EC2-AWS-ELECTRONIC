// src/app/pages/inscription/inscription.component.ts

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.css']
})
export class InscriptionComponent implements OnInit {
  
  registerForm!: FormGroup;
  loading = false;
  submitted = false;
  errorMessage = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Rediriger si déjà connecté
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/admin/dashboard']);
    }

    // Initialiser le formulaire
    this.registerForm = this.formBuilder.group({
      nomAdmin: ['', [Validators.required, Validators.minLength(2)]],
      prenomAdmin: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, {
      validator: this.passwordMatchValidator
    });
  }

  // Validateur personnalisé pour vérifier que les mots de passe correspondent
  passwordMatchValidator(group: FormGroup) {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    
    return password === confirmPassword ? null : { passwordMismatch: true };
  }

  // Getter pour accéder facilement aux champs du formulaire
  get f() { return this.registerForm.controls; }

  onSubmit(): void {
    this.submitted = true;
    this.errorMessage = '';

    // Arrêter si le formulaire est invalide
    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;

    // Préparer les données (sans confirmPassword)
    const registerData = {
      nomAdmin: this.f['nomAdmin'].value,
      prenomAdmin: this.f['prenomAdmin'].value,
      email: this.f['email'].value,
      password: this.f['password'].value
    };

    this.authService.register(registerData).subscribe({
      next: (response) => {
        console.log('✅ Inscription réussie', response);
        this.loading = false;
        
        // Rediriger vers la page de connexion avec message de succès
        this.router.navigate(['/connexion'], {
          state: { message: 'Inscription réussie ! Vous pouvez maintenant vous connecter.' }
        });
      },
      error: (error) => {
        this.errorMessage = error;
        this.loading = false;
      }
    });
  }
}