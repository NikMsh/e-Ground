import {Component, OnInit} from '@angular/core';
import {NgRedux, select} from '@angular-redux/store';
import {commentIsLoading, selectComments} from '../../store/selectors/offer.selector';
import {Observable} from 'rxjs';
import {Comment} from '../../model/Comment';
import {AppState} from '../../store';
import {ActivatedRoute} from '@angular/router';
import {fetchCommentsAction} from '../../store/actions/offer.actions';

@Component({
  selector: 'app-offer-feedback',
  templateUrl: './offer-feedback.component.html',
  styleUrls: ['./offer-feedback.component.css']
})
export class OfferFeedbackComponent implements OnInit {

  @select(selectComments)
  comments: Observable<Comment[]>;

  @select(commentIsLoading)
  isLoading: Observable<boolean>;

  constructor(private ngRedux: NgRedux<AppState>,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.ngRedux.dispatch(fetchCommentsAction(this.route.snapshot.paramMap.get('id')));
  }
}
