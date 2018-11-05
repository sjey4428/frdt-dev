package com.sysco.filter;

import org.apache.http.HttpStatus;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSAndSwaggerFilter implements Filter {
	
	private static final String USERNAME = "frdtadmin";
	private static final String PASSWORD = "Sysco123";
	private static final String SWAGGER_RESOURCE = "swagger-ui.html";
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		response.setHeader("Access-Control-Max-Age", "4800");
		response.setHeader("Access-Control-Allow-Headers",
		        "origin, Content-Type, X-Requested-With, X-File-Name, x-mime-type, Accept-Encoding, Authorization,Content-Range, Content-Disposition, Content-Description,Access-Control-Request-Method, Access-Control-Request-Headers");
		
		if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
			return;
		}
		
		// for swagger filter
		// need to check authorization only for swagger url		
		if (request.getRequestURL().toString().contains(SWAGGER_RESOURCE)) {
			String auth = request.getHeader("authorization");
			if (auth != null) {
				String authStr = auth.replaceAll("Basic ", "");
				byte[] authByte = Base64.getDecoder().decode(authStr);
				String newAuth = new String(authByte);
				String username = newAuth.split(":")[0];
				String password = newAuth.split(":")[1];
				if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
					response.setStatus(HttpStatus.SC_UNAUTHORIZED);
					response.setHeader("WWW-Authenticate", "Basic");
					return;
				} else {
					if (!USERNAME.equalsIgnoreCase(username) || !PASSWORD.equals(password)) {
						response.setStatus(HttpStatus.SC_UNAUTHORIZED);
						response.setHeader("WWW-Authenticate", "Basic");
						return;
					}
				}
			} else {
				response.setStatus(HttpStatus.SC_UNAUTHORIZED);
				response.setHeader("WWW-Authenticate", "Basic");
				return;
			}
			
		}//end		
		chain.doFilter(req, res);
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}
	
}