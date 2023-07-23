import { CartModelServer } from "./cart.model";
import { ClientModelServer } from "./client.model";

export interface UserModelServer {
    client: ClientModelServer;
    password: String;
    cart: CartModelServer;
}