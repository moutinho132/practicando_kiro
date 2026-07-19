import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <div class="container">
        <a class="navbar-brand fw-bold" routerLink="/">
          <i class="bi bi-heart-pulse me-2"></i>
          Registro Catástrofes
        </a>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav ms-auto">
            <li class="nav-item">
              <a class="nav-link" routerLink="/consulta" routerLinkActive="active" ariaCurrentWhenActive="page">
                <i class="bi bi-search me-1"></i>
                Consulta Pública
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/captura" routerLinkActive="active" ariaCurrentWhenActive="page">
                <i class="bi bi-person-plus me-1"></i>
                Registrar Persona
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  `,
  styles: [`
    .navbar-brand {
      font-size: 1.2rem;
    }
    .nav-link.active {
      font-weight: 600;
      border-bottom: 2px solid white;
    }
  `]
})
export class NavbarComponent {}
