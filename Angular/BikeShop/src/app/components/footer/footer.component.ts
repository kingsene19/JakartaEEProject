import { Component } from '@angular/core';
import { faMapMarker, faPhone, faEnvelopeOpen, faCreditCard, faCreditCardAlt} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})

export class FooterComponent {

  faMapMarker=faMapMarker;
  faPhone = faPhone;
  faEnvelopeOpen=faEnvelopeOpen;
  faCreditCard = faCreditCard;
  faCreditCardAlt = faCreditCardAlt;

}
