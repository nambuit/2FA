/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author tadekayero
 */
@WebService(serviceName = "TWOFASoapService")
public class TWOFASoapService {

    /**
     * This is a sample web service operation
     * @param username
     * @return 
     */
    @WebMethod(operationName = "Authenticate")
    public String Authenticate(@WebParam(name = "username") String username) {
         switch(username){
                case "DAVIES":
                    return "True";


                default:return"False";
            }
    }
}
