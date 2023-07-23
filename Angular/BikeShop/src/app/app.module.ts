import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HttpClientModule } from '@angular/common/http';
import { CategoriesComponent } from './components/categories/categories.component';
import { MarquesComponent } from './components/marques/marques.component';
import { SingleCardComponent } from './components/single-card/single-card.component';
import { ProductCardComponent } from './components/product-card/product-card.component';
import { ProductsComponent } from './components/products/products.component';
import { ProductsCategorieComponent } from './components/products-categorie/products-categorie.component';
import { ProductsMarqueComponent } from './components/products-marque/products-marque.component';
import { ProductsDetailsComponent } from './components/products-details/products-details.component';
import { CartComponent } from './components/cart/cart.component';
import { CartService } from './services/cart.service';
import { CommandesComponent } from './components/commandes/commandes.component';
import { FormsModule } from '@angular/forms';
import { ApiService } from './services/api.service';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { UsersService } from './services/users.service';
import { AuthService } from './services/auth.service';
import { ProfileComponent } from './components/profile/profile.component';
import { ListeCommandeComponent } from './components/liste-commande/liste-commande.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    CategoriesComponent,
    MarquesComponent,
    SingleCardComponent,
    ProductCardComponent,
    ProductsComponent,
    ProductsCategorieComponent,
    ProductsMarqueComponent,
    ProductsDetailsComponent,
    CartComponent,
    CommandesComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    ListeCommandeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [CartService, ApiService, UsersService, AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
