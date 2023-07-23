import { Component, Input} from '@angular/core';
import { CategorieModelServer } from 'src/app/models/categorie.model';
import { MarqueModelServer } from 'src/app/models/marque.model';

@Component({
  selector: 'app-single-card',
  templateUrl: './single-card.component.html',
  styleUrls: ['./single-card.component.scss']
})

export class SingleCardComponent{
  @Input() element!: CategorieModelServer | MarqueModelServer;
}
