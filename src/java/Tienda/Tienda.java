/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tienda;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author luigitercero
 */
@WebService(serviceName = "Tienda")
public class Tienda {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "inventario")
    public String inventario(@WebParam(name = "id") String id) {
        //TODO write your implementation code here:
                int max = 10; 
        int min = 1; 
        int range = max - min + 1; 
        int rand = (int)(Math.random() * range) + min;
        return "el producto id " + id + " tiene en su inventario " + rand    +" unidades";
    }
}
