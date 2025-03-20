import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { CartStore } from "./cart.store";
import { map, take } from "rxjs";


export const cartGuard: CanActivateFn = 
        (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
            const cartStore = inject(CartStore);
            const router = inject(Router);

            return cartStore.items$.pipe(
                take(1),
                map(items => {
                    if (items.length > 0) {
                        return true;
                    } else {
                        alert("🛒 Your cart is empty! Add items before checking out.");
                        // router.navigate([""])
                        return false;
                    }
                })
            )
        }