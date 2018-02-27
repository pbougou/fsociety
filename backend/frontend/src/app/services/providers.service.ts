import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';

import {Providers} from '../interfaces/providers.interface';

@Injectable()
export class ProvidersService {

  private static POST_URL = '/register/provider';
  private headers = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) {}

  createProvider(providers: Providers): Observable <any> {
    return this.http.post<any>(ProvidersService.POST_URL, providers, {observe : 'response'});
  }
}
