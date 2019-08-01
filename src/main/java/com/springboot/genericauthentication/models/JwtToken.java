/**
 * Class to define Jwt token
 */
package com.springboot.genericauthentication.models;
import java.util.UUID;
import lombok.Data;
/**
 * @author swathy
 *
 */
@Data
public class JwtToken {

	private UUID user_id;

	private String jwtToken;

}
