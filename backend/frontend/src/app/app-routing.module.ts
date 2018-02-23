import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ParentRegisterPageComponent} from './pages/parent-register-page/parent-register-page.component';
import { ProviderRegisterPageComponent } from './pages/provider-register-page/provider-register-page.component';
import { FrontPageComponent } from './pages/front-page/front-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { ParentEventsComponent} from './pages/parent-events/parent-events.component';
import { EventsMapComponent } from './pages/parent-events/events-map/events-map.component';
import { OneEventComponent } from './pages/one-event/one-event.component';
import { AuthGuardService } from './services/guards/auth-guard.service';
import { AdminAuthGuardService } from './services/guards/admin-auth-guard.service';

const appRoutes: Routes = [
  { 
    path: '', 
    component: FrontPageComponent 
  },
  { 
    path: 'parent-register', 
    component: ParentRegisterPageComponent 
  },
  { 
    path: 'provider-register', 
    component: ProviderRegisterPageComponent 
  },
  {
    path: 'login-page',
    component: LoginPageComponent
  },
  {
    path: 'parent-events', 
    component: ParentEventsComponent,
    canActivate: [AuthGuardService]
  },
  { 
    path: 'map', 
    component: EventsMapComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'one-event/:Id' ,
    component: OneEventComponent,
    canActivate: [AuthGuardService]
  },
  { 
    path: '**', 
    redirectTo : ''
  }
];

export const Routing = RouterModule.forRoot(appRoutes);
