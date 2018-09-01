package pl.toby.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        String authHeader = request.getHeader(JwtUtil.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith(JwtUtil.TOKEN_PREFIX)) {
            ((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Brak tokenu");
        }
        else {
            try {
                String token = authHeader.substring(JwtUtil.TOKEN_PREFIX.length());
                Claims claims = Jwts.parser().setSigningKey(JwtUtil.SECRET_KEY).parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(claims));
                chain.doFilter(req, res);
            }
            catch(Exception e) {
                ((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getLocalizedMessage());
            }
        }

    }

    private Authentication getAuthentication(Claims claims) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<String> roles = (List<String>) claims.get(JwtUtil.ROLE_CLAIM);

        authorities.addAll(roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(
                principal, "", authorities
        );
    }
}
