import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Offer} from '../model/Offer';

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  offer: Offer = {
    id: '213',
    description: 'asdasd',
    name: 'sdfgdfsg',
    price: 1000,
    category: 'rofl'
  };

  constructor() {
  }

  getOfferById(id: string): Observable<Offer> {
    return of(this.offer);
  }
}
