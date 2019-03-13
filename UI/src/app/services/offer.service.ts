import {Injectable} from '@angular/core';
import {Observable, of, throwError} from 'rxjs';
import {Offer} from '../model/Offer';
import {HttpClient} from '@angular/common/http';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class OfferService {
  apiUrl = '/api/v1/processor';

  offer: Offer = {
    id: '213',
    description: 'asdasd',
    name: 'sdfgdfsg',
    price: 1000,
    category: 'rofl'
  };

  constructor(private http: HttpClient) {
  }

  getOfferById(id: string): Observable<Offer> {
    return this.http.get<Offer>(`${this.apiUrl}/offers/${id}`)
      .pipe(catchError((error: any) => throwError(error.error)));
  }
}
