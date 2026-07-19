import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CapturaComponent } from './captura.component';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: '',
        component: CapturaComponent
      }
    ])
  ],
  declarations: [CapturaComponent],
  exports: [CapturaComponent]
})
export class CapturaModule {}
