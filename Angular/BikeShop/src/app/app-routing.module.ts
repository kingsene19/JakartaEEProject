import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { CategoriesComponent } from './components/categories/categories.component';
import { MarquesComponent } from './components/marques/marques.component';
import { ProductsComponent } from './components/products/products.component';
import { ProductsMarqueComponent } from './components/products-marque/products-marque.component';
import { ProductsCategorieComponent } from './components/products-categorie/products-categorie.component';
import { ProductsDetailsComponent } from './components/products-details/products-details.component';
import { CartComponent } from './components/cart/cart.component';
import { CommandesComponent } from './components/commandes/commandes.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ListeCommandeComponent } from './components/liste-commande/liste-commande.component';

const routes: Routes = [
  {
    path: '', component: HomeComponent
  },
  {
    path: 'register', component: RegisterComponent
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'profile', component: ProfileComponent
  },
  {
    path: 'liste-commandes', component: ListeCommandeComponent
  },
  {
    path: 'categories', component: CategoriesComponent
  },
  {
    path: 'categories/:slug', component: ProductsCategorieComponent
  },
  {
    path: 'marques', component: MarquesComponent
  },
  {
    path: 'marques/:slug', component: ProductsMarqueComponent
  },
  {
    path: 'produits', component: ProductsComponent
  },
  {
    path: 'produits/:id', component: ProductsDetailsComponent
  },
  {
    path: 'cart', component: CartComponent
  },
  {
    path: 'commande', component: CommandesComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
