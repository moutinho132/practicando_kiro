import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ConsultaComponent } from './consulta.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        component: ConsultaComponent
      }
    ])
  ],
  declarations: [ConsultaComponent],
  exports: [ConsultaComponent]
})
export class ConsultaModule {}
