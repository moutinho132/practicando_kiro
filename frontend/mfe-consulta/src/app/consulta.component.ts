import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-consulta',
  standalone: false,
  template: `
    <div class="container py-4">
      <div class="row justify-content-center">
        <div class="col-lg-10">
          <div class="card shadow mb-4">
            <div class="card-header bg-success text-white">
              <h2 class="mb-0">
                <i class="bi bi-search me-2"></i>
                Búsqueda Pública de Personas
              </h2>
            </div>
            <div class="card-body">
              <p class="text-muted mb-4">
                Busque personas registradas en el sistema. Puede buscar por nombre, cédula o ubicación.
              </p>
              
              <form [formGroup]="busquedaForm" (ngSubmit)="buscar()">
                <div class="row">
                  <div class="col-md-4 mb-3">
                    <label for="nombre" class="form-label">Nombres o Apellidos</label>
                    <input type="text" class="form-control" id="nombre" formControlName="nombre"
                           placeholder="Ej: Juan García">
                  </div>
                  <div class="col-md-4 mb-3">
                    <label for="cedula" class="form-label">Cédula</label>
                    <input type="text" class="form-control" id="cedula" formControlName="cedula"
                           placeholder="Ej: 1234567890">
                  </div>
                  <div class="col-md-4 mb-3">
                    <label for="ubicacion" class="form-label">Ubicación</label>
                    <input type="text" class="form-control" id="ubicacion" formControlName="ubicacion"
                           placeholder="Ej: Quito, Pichincha">
                  </div>
                </div>
                
                <div class="d-flex justify-content-end">
                  <button type="submit" class="btn btn-success">
                    <i class="bi bi-search me-1"></i>
                    Buscar
                  </button>
                </div>
              </form>
            </div>
          </div>

          <!-- Resultados de búsqueda -->
          <div *ngIf="busquedaRealizada" class="card shadow">
            <div class="card-header bg-light">
              <h5 class="mb-0">
                <i class="bi bi-people me-2"></i>
                Resultados de búsqueda ({{ resultados.length }})
              </h5>
            </div>
            <div class="card-body">
              <div *ngIf="resultados.length === 0" class="text-center py-5 text-muted">
                <i class="bi bi-inbox fs-1 d-block mb-3"></i>
                <p class="mb-0">No se encontraron personas con los criterios especificados.</p>
              </div>
              
              <div *ngIf="resultados.length > 0" class="row">
                <div *ngFor="let persona of resultados" class="col-md-6 col-lg-4 mb-3">
                  <div class="card h-100">
                    <div class="card-body">
                      <div class="d-flex align-items-start">
                        <div class="avatar-placeholder bg-secondary text-white rounded-circle d-flex align-items-center justify-content-center me-3"
                             style="width: 50px; height: 50px; font-size: 1.5rem;">
                          {{ persona.nombres.charAt(0) }}{{ persona.apellidos.charAt(0) }}
                        </div>
                        <div class="flex-grow-1">
                          <h6 class="mb-1">{{ persona.nombres }} {{ persona.apellidos }}</h6>
                          <p class="mb-1 small text-muted">
                            <i class="bi bi-geo-alt me-1"></i>
                            {{ persona.ubicacion }}
                          </p>
                          <span class="badge" [ngClass]="getEstadoClass(persona.estado)">
                            {{ persona.estado }}
                          </span>
                        </div>
                      </div>
                    </div>
                    <div class="card-footer bg-white">
                      <button class="btn btn-sm btn-outline-primary w-100" (click)="verDetalle(persona)">
                        <i class="bi bi-eye me-1"></i>
                        Ver detalles
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .avatar-placeholder {
      flex-shrink: 0;
    }
  `]
})
export class ConsultaComponent {
  busquedaForm: FormGroup;
  busquedaRealizada = false;
  enviando = false;
  
  resultados: any[] = [];

  constructor(private fb: FormBuilder) {
    this.busquedaForm = this.fb.group({
      nombre: [''],
      cedula: [''],
      ubicacion: ['']
    });
  }

  buscar(): void {
    this.enviando = true;
    this.busquedaRealizada = true;
    
    // Simulación de búsqueda - en producción sería una llamada al backend
    console.log('Búsqueda:', this.busquedaForm.value);
    
    setTimeout(() => {
      this.resultados = [
        {
          id: 1,
          nombres: 'Juan Carlos',
          apellidos: 'García López',
          ubicacion: 'Quito, Pichincha',
          estado: 'RESGUARDADO',
          fechaRegistro: new Date()
        },
        {
          id: 2,
          nombres: 'María Elena',
          apellidos: 'Martínez Ruíz',
          ubicacion: 'Cuenca, Azuay',
          estado: 'IDENTIFICADO',
          fechaRegistro: new Date()
        },
        {
          id: 3,
          nombres: 'Pedro Antonio',
          apellidos: 'Sánchez Torres',
          ubicacion: 'Guayaquil, Guayas',
          estado: 'HOSPITALIZADO',
          fechaRegistro: new Date()
        }
      ];
      this.enviando = false;
    }, 1000);
  }

  getEstadoClass(estado: string): string {
    const clases: { [key: string]: string } = {
      'DESAPARECIDO': 'bg-danger',
      'HERIDO': 'bg-warning text-dark',
      'HOSPITALIZADO': 'bg-info',
      'RESGUARDADO': 'bg-primary',
      'IDENTIFICADO': 'bg-success'
    };
    return clases[estado] || 'bg-secondary';
  }

  verDetalle(persona: any): void {
    console.log('Ver detalle de:', persona);
    // En producción, esto navegaría a una vista de detalle
    alert(`Detalle de: ${persona.nombres} ${persona.apellidos}\nEstado: ${persona.estado}\nUbicación: ${persona.ubicacion}`);
  }
}
