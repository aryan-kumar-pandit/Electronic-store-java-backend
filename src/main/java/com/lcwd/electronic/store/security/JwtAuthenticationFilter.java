package com.lcwd.electronic.store.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private Logger logger= LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //this will run before api runs.to verify Jwt header
        //Authorization : Bearer fjhfjvfjvjfjvfkvs
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header {}",requestHeader);
        String username=null;
        String token=null;
        if(requestHeader!=null && requestHeader.startsWith("Bearer"))
        {
            token=requestHeader.substring(7);
            try{
                username = jwtHelper.getUsernameFromToken(token);
                logger.info("Username {}",username);
            }
            catch(IllegalArgumentException ex)
            {
                logger.info("illegal argument while fetching username");
            }
            catch(ExpiredJwtException e)
            {
                logger.info("Jwt token has expired");
            }
            catch (MalformedJwtException e)
            {
                logger.info("Someone has tampared token");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            // if username is not null and securityContext is not null, it means authentication is not set
            // so we need to set authentication
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //validate token
            if(username.equals(userDetails.getUsername()) && !jwtHelper.isTokenExpired(token))
            {
                //token valid
                //set authentication in securityContext
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request,response);//this will forward request to Api's
    }
}
