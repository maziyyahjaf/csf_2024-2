import { Component, inject, OnInit } from '@angular/core';
import { CartStore } from '../cart.store';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LineItem, Order } from '../models';
import { ProductService } from '../product.service';
import { ApiResponse } from '../api-response';
import { Router } from '@angular/router';

@Component({
  selector: 'app-confirm-checkout',
  templateUrl: './confirm-checkout.component.html',
  styleUrl: './confirm-checkout.component.css'
})
export class ConfirmCheckoutComponent implements OnInit {
  

  // TODO Task 3
  cartStore = inject(CartStore);
  productService = inject(ProductService);
  router = inject(Router);
  items$ = this.cartStore.items$;
  total$ = this.cartStore.total$;

  fb = inject(FormBuilder);
  checkoutForm!: FormGroup;

  items: LineItem[] = [];

 


  ngOnInit(): void {
    this.checkoutForm = this.createCheckoutForm();
    this.items$.subscribe(items => {
      this.items = items; // items from the cart store assigned to my items property array
      this.checkoutForm.patchValue({cartItems: this.items}); //updating the form 
    })
  }

  createCheckoutForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      address: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      priority: this.fb.control<boolean>(false),
      comments: this.fb.control<string>(''),
      cartItems: this.fb.control<LineItem[]>([])
    })

  }

  processCheckoutForm() {
    const orderDetails: Order = {
      name: this.checkoutForm.get('name')?.value || '',
      address: this.checkoutForm.get('address')?.value || '',
      priority: this.checkoutForm.get('priority')?.value || false,
      comments: this.checkoutForm.get('comments')?.value || '',
      cart: {lineItems: this.checkoutForm.get('cartItems')?.value}
    }

    this.productService.checkout(orderDetails).subscribe({
      next: (data: any) => {
        console.log(data);
        // how to extract the order id?
        const orderId = data.orderId;
        alert(`Order created successfully! Order ID: ${orderId}`);
        this.router.navigate(['categories']);
        this.cartStore.resetCart;

    
      },
      error: (error) => {
        console.error(error.message);
        const errorMessage = error.error?.message || "Checkout failed. Please try again.";
        alert(errorMessage);
      } 
    });


      
  }
  


}
