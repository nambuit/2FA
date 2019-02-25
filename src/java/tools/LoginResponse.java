/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author inlaks-root
 */
@Getter @Setter
public class LoginResponse {
   private String response_channel;
   private String response_code;
   private String response_message;    
}
