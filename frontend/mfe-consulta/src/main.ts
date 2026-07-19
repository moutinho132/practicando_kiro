import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { ConsultaModule } from './app/consulta.module';

platformBrowserDynamic()
  .bootstrapModule(ConsultaModule)
  .catch((err) => console.error(err));
