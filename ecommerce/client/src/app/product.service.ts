import {Injectable, inject} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Order, Product} from "./models";

@Injectable()
export class ProductService {

  private http = inject(HttpClient)


  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  getProductCategories(): Observable<string[]> {
    return this.http.get<string[]>('/api/categories')
  }

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  getProductsByCategory(category: string): Observable<Product[]> {
    return this.http.get<Product[]>(`/api/category/${category}`)
  }

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  checkout(order: Order) {
    // TODO Task 3
    console.log("sending order to backend", order);
    const httpHeaders = new HttpHeaders()
                        .set("Content-Type", "application/json")
                        .set("Accept", "application/json");

    return this.http.post('/api/order', order, {headers: httpHeaders})
  }
}
