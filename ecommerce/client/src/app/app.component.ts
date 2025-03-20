import { Component, OnInit, inject } from '@angular/core';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';
import { CartStore } from './cart.store';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  // NOTE: you are free to modify this component

  private router = inject(Router)
  cartStore = inject(CartStore)

  itemCount!: number
  items$ = this.cartStore.items$;
  uniqueItems$ = this.cartStore.uniqueItems$;
  canCheckout$ = this.cartStore.canCheckout$;


  ngOnInit(): void {
  }

  checkout(): void {
    this.router.navigate([ '/checkout' ])
  }

  // validToCheckout(): boolean {
  //   if (this.items$.subscribe.length > 0) {
  //     return true;
  //   }
  //   return false;
  // }
  // [disabled]="(items$ | async)?.length"
  // [disabled]="!(canCheckout$ | async)"
}
