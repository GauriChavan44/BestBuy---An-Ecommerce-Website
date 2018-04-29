package neu.edu.controller;


import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import neu.edu.controller.data.AuthRequest;
import neu.edu.controller.data.RestLogicalErrorException;
import neu.edu.controller.data.UserSession;
import neu.edu.interceptor.SecurityInterceptor;
import neu.edu.service.AuthenticationService;

@Path("/auth")
@Controller
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationController {	
	
	
	
	private Logger logger = Logger.getLogger(AuthenticationController.class);
	
	@Autowired
	private AuthenticationService authenticationService;
	
	
	@POST
	public Response authenticate(AuthRequest authRequest,
								@HeaderParam("Authorization") String authorization,
								@Context HttpServletRequest servletRequest){
		//String authorization =servletContext.getHeader("Authorization"); -- Alternative
		
		String username=null,password=null;
		if (authorization == null) {
			username = authRequest.getUsername();
			password = authRequest.getPassword();

			//Response.ok().entity(SecurityInterceptor.ACCESS_DENIED).build();
		} else {
			String authorizationValue = null;
			
			try {
				
				 String[] authorizationParts = authorization.split("\\s+");
				if (authorizationParts[0].trim().equals("Basic")) {
					String authorizationInfo = authorizationParts[1];
					authorizationValue = new String(Base64.decode(authorizationInfo));
					username = authorizationValue.split("\\:")[0];
					password = authorizationValue.split("\\:")[1];
				}

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		UserSession userSession = null;
		String token = null;
		try {
			userSession = authenticationService.checkWhichUserLoggingIn(username, password);
			token = authenticationService.generateAuthenticationToken(userSession,servletRequest.getRemoteAddr());
			ServletContext servletContext =  servletRequest.getServletContext();

			servletContext.setAttribute(token, userSession);
			logger.info("User "+"");

		} catch (RestLogicalErrorException e) {
			logger.error("Login Failed"+e.getResponseError().getMessage());
			// TODO Auto-generated catch block
			return Response.ok().entity(e.getResponseError()).build();

		}
		
		
		
		return Response.ok()
						.entity(userSession)
						.header(SecurityInterceptor.AUTHORIZATION_PROPERTY, token)
						.build();
	}

}
