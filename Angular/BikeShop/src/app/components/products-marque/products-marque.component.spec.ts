import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsMarqueComponent } from './products-marque.component';

describe('ProductsMarqueComponent', () => {
  let component: ProductsMarqueComponent;
  let fixture: ComponentFixture<ProductsMarqueComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductsMarqueComponent]
    });
    fixture = TestBed.createComponent(ProductsMarqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
