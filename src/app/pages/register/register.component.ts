import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service'; // Chemin vers votre AuthService
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  signRequest = {
    firstname: '',
    lastname: '',
    email: '',
    password: ''
  };

  // Propriétés pour les messages
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {}

  onRegister(): void {
    // Réinitialisez les messages
    this.successMessage = '';
    this.errorMessage = '';

    this.authService.register(this.signRequest).subscribe(
      (response) => {
        // Message de succès
        this.successMessage = 'Inscription réussie! Vous serez redirigé vers la page de connexion.';
        console.log('Inscription réussie:', response);

        // Redirection après un délai
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      (error) => {
        // Message d'erreur
        this.errorMessage = "Une erreur s'est produite lors de l'inscription. Veuillez réessayer.";
        console.error('Erreur lors de l’inscription:', error);
      }
    );
  }
}
