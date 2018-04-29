package neu.edu.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.core.interception.PostMatchContainerRequestContext;
import org.springframework.stereotype.Component;

import neu.edu.controller.data.UserSession;

@Provider
@Component
public class SecurityInterceptor implements ContainerRequestFilter {

	@Context
	private HttpServletRequest servletRequest;
	
	public static final String AUTHORIZATION_PROPERTY = "auth-token";
	public static final String ROLE_CUSTOMER = "customer";

	public static final String ROLE_MERCHANT = "merchant";

	

	public static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401,new Headers<Object>());;
	public static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Can not access this resource", 403,new Headers<Object>());;
	public static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500,new Headers<Object>());;

	
	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		// Reflection accessing method
		PostMatchContainerRequestContext pmContext = (PostMatchContainerRequestContext) context;
		Method method = pmContext.getResourceMethod().getMethod();
		
		System.out.println("**SecurityInterceptor**");
		
		boolean securityAnnotationPresent = false;
		
		//This is blocking of method. ( Maybe old impl or future )
		if(method.isAnnotationPresent(DenyAll.class)){
			securityAnnotationPresent = true;
			System.out.println("Method is Blocked");
			context.abortWith(ACCESS_DENIED);
			return;
		}
		
		//This is Public Access no Authorization required.
		if(method.isAnnotationPresent(PermitAll.class)){
			System.out.println("Method is Public");
			securityAnnotationPresent = true;
			return;
		}
		
		MultivaluedMap<String, String> headers = context.getHeaders();
		String authKey = headers.getFirst(AUTHORIZATION_PROPERTY);
		if(authKey == null){
			context.abortWith(ACCESS_DENIED);
			return;
		}
		
		Set<String> allowedRoleSet= null;
		if(method.isAnnotationPresent(RolesAllowed.class)){
			securityAnnotationPresent = true;
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            allowedRoleSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
            
			if (!validateRole(authKey,allowedRoleSet)) {
				context.abortWith(ACCESS_FORBIDDEN);
				return;
			}

		}
		
		//If any method is missing any security annotation block by default
		if(!securityAnnotationPresent){
			System.out.println("Method is Doesnt have a Security Role Associated");

			context.abortWith(ACCESS_DENIED);
			return;
		}
		
		
		
		
	}


	private boolean validateRole(String authKey, Set<String> allowedRoleSet) {
		// TODO Auto-generated method stub
		ServletContext servletContext =  servletRequest.getServletContext();
		
		
		UserSession userSession = (UserSession) servletContext.getAttribute(authKey);
		
		if(allowedRoleSet.contains(userSession.getRole())){
			return true;
		}
		
		return false;
	}

	
}
