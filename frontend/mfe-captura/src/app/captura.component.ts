import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-captura',
  standalone: false,
  template: `
    <div class="container py-4">
      <div class="row justify-content-center">
        <div class="col-lg-8">
          <div class="card shadow">
            <div class="card-header bg-primary text-white">
              <h2 class="mb-0">
                <i class="bi bi-person-plus me-2"></i>
                Registro de Persona Afectada
              </h2>
            </div>
            <div class="card-body">
              <p class="text-muted mb-4">
                Complete el formulario para registrar a una persona afectada por la catástrofe.
                Los campos marcados con * son obligatorios.
              </p>
              
              <form [formGroup]="personaForm" (ngSubmit)="onSubmit()">
                <!-- Tipo de persona -->
                <div class="mb-3">
                  <label class="form-label fw-bold">Tipo de registro *</label>
                  <div class="form-check">
                    <input class="form-check-input" type="radio" formControlName="tipoPersona" value="adulto" id="adulto">
                    <label class="form-check-label" for="adulto">Persona adulta</label>
                  </div>
                  <div class="form-check">
                    <input class="form-check-input" type="radio" formControlName="tipoPersona" value="menor" id="menor">
                    <label class="form-check-label" for="menor">Menor de edad</label>
                  </div>
                </div>

                <!-- Datos personales -->
                <div class="row">
                  <div class="col-md-6 mb-3">
                    <label for="nombres" class="form-label">Nombres *</label>
                    <input type="text" class="form-control" id="nombres" formControlName="nombres"
                           placeholder="Ej: Juan Carlos">
                  </div>
                  <div class="col-md-6 mb-3">
                    <label for="apellidos" class="form-label">Apellidos *</label>
                    <input type="text" class="form-control" id="apellidos" formControlName="apellidos"
                           placeholder="Ej: García López">
                  </div>
                </div>

                <div class="row">
                  <div class="col-md-6 mb-3">
                    <label for="cedula" class="form-label">Cédula de identidad</label>
                    <input type="text" class="form-control" id="cedula" formControlName="cedula"
                           placeholder="Ej: 1234567890">
                    <div class="form-text">Obligatorio para adultos. Opcional para menores.</div>
                  </div>
                  <div class="col-md-6 mb-3">
                    <label for="fechaNacimiento" class="form-label">Fecha de nacimiento *</label>
                    <input type="date" class="form-control" id="fechaNacimiento" formControlName="fechaNacimiento">
                  </div>
                </div>

                <div class="row">
                  <div class="col-md-6 mb-3">
                    <label for="genero" class="form-label">Género *</label>
                    <select class="form-select" id="genero" formControlName="genero">
                      <option value="">Seleccione...</option>
                      <option value="M">Masculino</option>
                      <option value="F">Femenino</option>
                      <option value="O">Otro</option>
                    </select>
                  </div>
                  <div class="col-md-6 mb-3">
                    <label for="telefono" class="form-label">Teléfono de contacto</label>
                    <input type="tel" class="form-control" id="telefono" formControlName="telefono"
                           placeholder="Ej: +593 99 123 4567">
                  </div>
                </div>

                <!-- Ubicación -->
                <h5 class="mt-4 mb-3">
                  <i class="bi bi-geo-alt me-2"></i>
                  Ubicación actual
                </h5>

                <div class="mb-3">
                  <button type="button" class="btn btn-outline-primary" (click)="obtenerUbicacionGPS()">
                    <i class="bi bi-crosshair me-1"></i>
                    Obtener mi ubicación GPS
                  </button>
                  <span *ngIf="obteniendoUbicacion" class="ms-2">
                    <span class="spinner-border spinner-border-sm" role="status"></span>
                    Obteniendo ubicación...
                  </span>
                </div>

                <div class="row">
                  <div class="col-md-6 mb-3">
                    <label for="provincia" class="form-label">Provincia *</label>
                    <input type="text" class="form-control" id="provincia" formControlName="provincia"
                           placeholder="Ej: Pichincha">
                  </div>
                  <div class="col-md-6 mb-3">
                    <label for="canton" class="form-label">Cantón *</label>
                    <input type="text" class="form-control" id="canton" formControlName="canton"
                           placeholder="Ej: Quito">
                  </div>
                </div>

                <div class="row">
                  <div class="col-md-6 mb-3">
                    <label for="parroquia" class="form-label">Parroquia</label>
                    <input type="text" class="form-control" id="parroquia" formControlName="parroquia"
                           placeholder="Ej: Centro histórico">
                  </div>
                  <div class="col-md-6 mb-3">
                    <label for="direccion" class="form-label">Dirección detallada</label>
                    <input type="text" class="form-control" id="direccion" formControlName="direccion"
                           placeholder="Calle, número, referencia">
                  </div>
                </div>

                <!-- Estado -->
                <div class="mb-3">
                  <label for="estado" class="form-label fw-bold">Estado actual *</label>
                  <select class="form-select" id="estado" formControlName="estado">
                    <option value="">Seleccione...</option>
                    <option value="DESAPARECIDO">Desaparecido</option>
                    <option value="HERIDO">Herido</option>
                    <option value="HOSPITALIZADO">Hospitalizado</option>
                    <option value="RESGUARDADO">Resguardado en albergue</option>
                    <option value="IDENTIFICADO">Identificado</option>
                  </select>
                </div>

                <!-- Notas adicionales -->
                <div class="mb-4">
                  <label for="notas" class="form-label">Notas adicionales</label>
                  <textarea class="form-control" id="notas" formControlName="notas" rows="3"
                            placeholder="Observaciones, condiciones médicas especiales, etc."></textarea>
                </div>

                <!-- Botones de acción -->
                <div class="d-flex gap-3 justify-content-end">
                  <button type="button" class="btn btn-secondary" (click)="limpiarFormulario()">
                    <i class="bi bi-x-circle me-1"></i>
                    Limpiar
                  </button>
                  <button type="submit" class="btn btn-primary" [disabled]="personaForm.invalid || enviando">
                    <span *ngIf="enviando" class="spinner-border spinner-border-sm me-1"></span>
                    <i *ngIf="!enviando" class="bi bi-check-circle me-1"></i>
                    Registrar Persona
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  `
})
export class CapturaComponent {
  personaForm: FormGroup;
  enviando = false;
  obteniendoUbicacion = false;

  constructor(private fb: FormBuilder) {
    this.personaForm = this.fb.group({
      tipoPersona: ['adulto', Validators.required],
      nombres: ['', Validators.required],
      apellidos: ['', Validators.required],
      cedula: [''],
      fechaNacimiento: ['', Validators.required],
      genero: ['', Validators.required],
      telefono: [''],
      provincia: ['', Validators.required],
      canton: ['', Validators.required],
      parroquia: [''],
      direccion: [''],
      estado: ['', Validators.required],
      notas: ['']
    });
  }

  obtenerUbicacionGPS(): void {
    if (!navigator.geolocation) {
      alert('Su navegador no soporta geolocalización');
      return;
    }

    this.obteniendoUbicacion = true;
    navigator.geolocation.getCurrentPosition(
      (position) => {
        console.log('Ubicación obtenida:', position.coords.latitude, position.coords.longitude);
        // Aquí se podría hacer geocodificación inversa para llenar provincia/cantón
        this.obteniendoUbicacion = false;
      },
      (error) => {
        console.error('Error obteniendo ubicación:', error);
        alert('No se pudo obtener su ubicación. Por favor ingrésela manualmente.');
        this.obteniendoUbicacion = false;
      }
    );
  }

  onSubmit(): void {
    if (this.personaForm.valid) {
      this.enviando = true;
      console.log('Formulario enviado:', this.personaForm.value);
      // Aquí se llamaría al servicio para enviar al backend
      setTimeout(() => {
        this.enviando = false;
        alert('Persona registrada exitosamente');
        this.limpiarFormulario();
      }, 1500);
    }
  }

  limpiarFormulario(): void {
    this.personaForm.reset({
      tipoPersona: 'adulto'
    });
  }
}
