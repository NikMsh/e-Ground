import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Offer} from '../model/Offer';
import {HttpClient} from '@angular/common/http';
import {Comment} from '../model/Comment';

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

  comments: Comment[] = [
    {id: '111', customerId: '1', message: 'hello'},
    {id: '222', customerId: '2', message: 'men'},
    {id: '333', customerId: '3', message: 'hegddllo'},
    {id: '444', customerId: '4', message: 'bad'},
    {id: '555', customerId: '5', message: 'cry'}
  ];

  constructor(private http: HttpClient) {
  }

  getOfferById(offerId: string): Observable<Offer> {
    /*return this.http.get<Offer>(`${this.apiUrl}/offers/${offerId}`)
      .pipe(catchError((error: any) => throwError(error.error)));*/
    return of(this.offer);
  }

  getOfferComments(offerId: string): Observable<Comment[]> {
    return of(this.comments);
  }
}
