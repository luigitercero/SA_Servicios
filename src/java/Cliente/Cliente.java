/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author luigitercero
 */
@WebService(serviceName = "Cliente")
public class Cliente {

    /**
     * Web service operation
     */
    
     private final static String QUEUE_NAME = "hello";
    @WebMethod(operationName = "Saldo")
    public String Saldo(@WebParam(name = "id") String id) {
        //TODO write your implementation code here:
         int max = 10; 
        int min = 1; 
        int range = max - min + 1; 
        int rand = (int)(Math.random() * range) + min;
        String cola ="";
        String output = "" ;
         try {
             Eviar(id);
           output =Recibircol(id);
             cola= "enviada";
                     } catch (IOException ex) {
             Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                          cola= "errro 1"+ex.getMessage();

         } catch (TimeoutException ex) {
             Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
             cola= "error 2";
         }
        return cola + " el saldo del cliente " + output + " es :)"+ rand;
    }
    
    private String Recibircol(String dato) throws IOException, TimeoutException {
         ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.17.0.2");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        String output = "";
        ArrayList <String>list = new <String>ArrayList();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            list.add(message);
            System.out.println(" [x] Received '" + message + "'");
            
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        
         return list.get(0)+"";
    }
    
    
    private String Eviar(String dato) throws IOException, TimeoutException {
String message = dato;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.17.0.2");
      
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
        return message;
    }
}
