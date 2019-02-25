/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authservice;

import com.google.gson.Gson;
import java.util.Date;
import javax.naming.InitialContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import tools.AuthenticateClass;
import tools.LoginClass;
import tools.LoginResponse;
import tools.RestClient;
import tools.UserClass;

/**
 * REST Web Service
 *
 * @author inlaks-root
 */
@Path("ISECInterface")
public class ISECInterface {

    @Context
    private UriInfo context;
    private String api_key;
    private String client_id;
    /**
     * Creates a new instance of ISECInterface
     */
    public ISECInterface() {

         try
    {

        javax.naming.Context ctx = (javax.naming.Context)new InitialContext().lookup("java:comp/env");
        client_id = (String)ctx.lookup("client_id");
        api_key = (String)ctx.lookup("api_key");


    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
    }

    /**
     * Retrieves representation of an instance of authservice.ISECInterface
     * @param auth  
     * @return an instance of java.lang.String
     */
    @POST
    @Path("Authenticate")
    @Produces(MediaType.APPLICATION_JSON)
    public String Authenticate(String data) {
          try{
       
              LoginClass login = new LoginClass();
              login.setApi_key(api_key);
              login.setClient_id(client_id);
              
              Gson gson = new Gson();
              
            AuthenticateClass auth = (AuthenticateClass) gson.fromJson(data, AuthenticateClass.class);
            
            
              
              
              login.setCustomerId(auth.getUserID());

              Date now = new Date();

              login.setTime(now.toString());
              
              RestClient rest = new RestClient();
             String payload =  gson.toJson(login);
           String result =   rest.ProcessRequest(payload, "login");
           LoginResponse responseobject = gson.fromJson(result,LoginResponse.class);
         
          


       switch(responseobject.getResponse_code()){
                case "00":
                   // responseobject.setResponse_code(responseobject.getResponse_code());
                  //  responseobject.setResponse_message("User Authenticated Sucessfuly");
                    return "True";


                default:
                  
                    return  "False";
            }


        }
        catch(Exception d){
            return "False";
        }
    }

@GET
    @Path("CreateUser")
    @Consumes(MediaType.APPLICATION_XML)
    public String CreateUser(@QueryParam("id")String username, @QueryParam("name")String name, @QueryParam("email")String email, @QueryParam("phoneno")String phoneno) {
          try{

              UserClass user = new UserClass();
              user.setApi_key(api_key);
              user.setClient_id(client_id);
              user.setUsername(username);
              user.setName(name);
              user.setPhoneNumber(phoneno);
              user.setEmail(email);
              user.setCustomerID(username);
//              Date now = new Date();
//
//              user(now.toString());
              Gson gson = new Gson();
              RestClient rest = new RestClient();
             String payload =  gson.toJson(user);
           String result =   rest.ProcessRequest(payload, "profile/create");
           LoginResponse responseobject = gson.fromJson(result,LoginResponse.class);

           return responseobject.getResponse_message();

        }
        catch(Exception d){
            return d.getMessage();
        }
    }

}
