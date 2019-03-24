/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rabbi;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.util.ArrayList;

/**
 *
 * @author luigitercero
 */
@WebService(serviceName = "Cola")
public class Cola {

    private final static String QUEUE_NAME = "hello";

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Enviar")
    public String Enviar(@WebParam(name = "parameter") String parameter) {
        try {
            //TODO write your implementation code here:
            Eviar(parameter);
        } catch (IOException ex) {
            Logger.getLogger(Cola.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(Cola.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "no se envio nada :'( ";
    }

    private String Eviar(String dato) throws IOException, TimeoutException {
String message = dato;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(15672);
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
        return message;
    }
    
    private String Recibircol(String dato) throws IOException, TimeoutException {
         ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(15672);
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
    
    private String Recibir(String dato) throws TimeoutException {
        try {
            String salida = Recibircol(dato);
            return "";
        } catch (IOException ex) {
            Logger.getLogger(Cola.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "no hay nada";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Recibi")
    public String Recibi(@WebParam(name = "parameter") String parameter) {
        //TODO write your implementation code here:
        return null;
    }
}
