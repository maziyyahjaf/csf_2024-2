
// TODO Task 2
// Use the following class to implement your store

import { Injectable } from "@angular/core";
import { ComponentStore } from "@ngrx/component-store";
import { Cart, LineItem } from "./models";

export interface CartState {
    items: Cart;
    // lineItems: LineItem[];
    uniqueItems: number;
}

@Injectable({
    providedIn: 'root'
})
export class CartStore extends ComponentStore<CartState>{
    constructor() {
        super({items: {lineItems: []}, uniqueItems: 0})
    }

    readonly items$ = this.select((state) => state.items.lineItems);
    readonly uniqueItems$ = this.select((state) => state.uniqueItems);
    readonly canCheckout$ = this.select(this.items$, (items) => items.length > 0)
    readonly total$ = this.select(
        this.items$,
        (items) => items.reduce((acc, item) => acc + item.price, 0)
    )

    

    readonly addItem = this.updater<LineItem>((state, item: LineItem) => {
        const newCartState = {
            ...state,
        items: {lineItems: [...state.items.lineItems, item]},
        uniqueItems: (state.items.lineItems.some(lineItem => lineItem.prodId === item.prodId) 
                        ? state.uniqueItems 
                        : state.uniqueItems + 1)
        };

        console.log("🛒 Updated Cart State:", newCartState);
        return newCartState;
    })

    readonly resetCart = this.updater(() => {
        const emptyCart = {
            items: {lineItems: []},
            uniqueItems: 0,
        }

        return emptyCart;
    })

    readonly addItemNoDuplicates = this.updater<LineItem>((state, item: LineItem) => {
        const newCartState = {
            ...state,
            items: {
                ...state.items,
                lineItems: state.items.lineItems.some(lineItem => lineItem.prodId === item.prodId)
                            ? state.items.lineItems.map(lineItem => lineItem.prodId === item.prodId 
                                ? {...lineItem, quantity: lineItem.quantity + item.quantity} : lineItem) 
                                : [...state.items.lineItems, item]
            },
            uniqueItems: (state.items.lineItems.some(lineItem => lineItem.prodId === item.prodId) 
            ? state.uniqueItems 
            : state.uniqueItems + 1)
        }

        console.log("🛒 Updated Cart State:", newCartState);
        return newCartState;
    })

    

    
}
