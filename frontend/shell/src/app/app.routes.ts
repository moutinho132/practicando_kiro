import { Routes } from '@angular/router';
import { loadRemote } from '@angular-architects/module-federation';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/consulta',
    pathMatch: 'full'
  },
  {
    path: 'captura',
    loadChildren: () =>
      loadRemote('mfeCaptura', './Module').then((m) => m.CapturaModule)
  },
  {
    path: 'consulta',
    loadChildren: () =>
      loadRemote('mfeConsulta', './Module').then((m) => m.ConsultaModule)
  },
  {
    path: '**',
    redirectTo: '/consulta'
  }
];
