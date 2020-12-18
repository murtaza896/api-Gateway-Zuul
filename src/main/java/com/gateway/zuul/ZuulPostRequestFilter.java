package com.gateway.zuul;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulPostRequestFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
        
        return true;
				
	}

	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		RequestContext ctx = RequestContext.getCurrentContext();
		System.out.println("I am in post filter");
        HttpServletRequest req = ctx.getRequest();
//        HttpServletResponse res = ctx.getResponse();
//        System.out.println(res.toString());
//        ctx.setResponseGZipped(true);
//        InputStream compressedResponseDataStream = ctx.getResponseDataStream();
//        System.out.println(ctx.getResponseBody());
        try 
        {
//        	InputStream responseDataStream = new GZIPInputStream(compressedResponseDataStream);
//            String responseAsString = StreamUtils.copyToString(responseDataStream, Charset.forName("UTF-8"));
//            // Do want you want with your String response
//            System.out.println(responseAsString);
//            // Replace the response with the modified object
//            ctx.setResponseBody(responseAsString);
            
//        	Iterator<String> itr = res.getHeaderNames().iterator();
//        	while(itr.hasNext())
//        	{
//                Object element = itr.next();
//                System.out.println(element + " ");
//            }
        }
        catch(Exception e)
        {
        	System.out.println(e.getMessage());
        }
        ctx.getResponse().setHeader("Set-Cookie", "uvw=xyz");
//        try {
//        	for(Cookie x: req.getCookies())
//        	{
//        		ctx.getResponse().addCookie(x);
//        		System.out.println(x.getName() + ": " + x.getValue());
//        	}
//        }
//        catch(Exception e)
//        {
//        	System.out.println("FML");
//        }
        
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "post";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
