import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {Offer} from '../model/Offer';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CatalogService {
  apiUrl = '/api/v1/processor';

  offers: Offer[] = [
    {id: '1', name: 'qwe1', price: 100, category: 'TOOLS'},
    {id: '2', name: 'qwe2', price: 1000, category: 'TOOLS'},
    {id: '3', name: 'qwe3', price: 10000, category: 'TOOLS'},
    {id: '4', name: 'qwe4', price: 10, category: 'TOOLS'},
    {id: '5', name: 'qwe5', price: 60, category: 'TOOLS'},
    {id: '6', name: 'qwe6', price: 180, category: 'TOOLS'}
  ];

  constructor(private http: HttpClient) {
  }

  getCatalogList(): Observable<Offer[]> {
    return this.http.get<Offer[]>(`${this.apiUrl}/offers/filter`)
      .pipe(catchError((error: any) => throwError(error.error)));
    // return of(this.offers);
  }

  createOffer(offer: Offer): Observable<Offer> {
    return this.http.post<Offer>(`${this.apiUrl}/offers`, offer)
      .pipe(catchError((error: any) => throwError(error)));
  }
}
