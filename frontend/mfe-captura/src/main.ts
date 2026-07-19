import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { CapturaModule } from './app/captura.module';

platformBrowserDynamic()
  .bootstrapModule(CapturaModule)
  .catch((err) => console.error(err));
