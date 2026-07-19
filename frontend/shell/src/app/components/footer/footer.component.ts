import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  standalone: true,
  template: `
    <footer class="bg-dark text-white py-4 mt-5">
      <div class="container">
        <div class="row">
          <div class="col-md-6">
            <h5 class="fw-bold">Sistema de Registro de Personas en Catástrofes</h5>
            <p class="mb-1 small text-muted">
              Facilita el registro y localización de personas afectadas por desastres naturales.
            </p>
          </div>
          <div class="col-md-6 text-md-end">
            <p class="mb-1 small">
              <i class="bi bi-envelope me-1"></i>
              contacto@registrocatastrofes.org
            </p>
            <p class="mb-0 small text-muted">
              &copy; {{ currentYear }} - Todos los derechos reservados
            </p>
          </div>
        </div>
      </div>
    </footer>
  `
})
export class FooterComponent {
  currentYear = new Date().getFullYear();
}
