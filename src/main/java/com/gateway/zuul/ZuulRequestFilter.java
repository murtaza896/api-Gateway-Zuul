package com.gateway.zuul;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulRequestFilter extends ZuulFilter {

	@Autowired 
	private EurekaClient eurekaClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();
		
		String reqURI = req.getRequestURI();
		return !(reqURI.startsWith("/auth") || reqURI.startsWith("/user"));
	}
	
	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest req = ctx.getRequest();

    	InstanceInfo instance = eurekaClient.getNextServerFromEureka("AUTH-SERVICE", false);
		
		String hosturi = instance.getHomePageUrl(); //http://localhost:8080
		
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> response = null;
		try
		{
			for(Cookie x: req.getCookies())
				headers.add("Cookie", x.getName() + "=" + x.getValue());
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			response = restTemplate.exchange(hosturi + "user/get-user", HttpMethod.GET, entity, String.class);
		}
		catch(Exception e)
		{
			System.out.println("No cookie found");
		}
		
		
			if (response == null || response.getStatusCode().compareTo(HttpStatus.BAD_REQUEST) == 0)
	        {
	            // blocks the request
	            ctx.setSendZuulResponse(false);
	
	            // response to client
	            ctx.setResponseBody("Not authorized");
	            ctx.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
	            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
	        }
	
		
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}
