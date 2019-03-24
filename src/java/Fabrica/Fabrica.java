/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fabrica;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author luigitercero
 */
@WebService(serviceName = "Fabrica")
public class Fabrica {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "TiempoDeFabricacion")
    public String TiempoDeFabricacion(@WebParam(name = "id") String id) {
        //TODO write your implementation code here:
        
        int max = 10; 
        int min = 1; 
        int range = max - min + 1; 
        int rand = (int)(Math.random() * range) + min;
        return "El producto " + id +" estara listo para " + rand+"/"+rand;
    }
}
