package pnu.cse.cloudchain.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import pnu.cse.cloudchain.gateway.exception.JwtTokenMissingException;

import java.util.Date;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;
    String secretKey;

    @Autowired
    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
        this.secretKey = env.getProperty("token.secret_key");
    }
    public static class Config {

    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw new JwtTokenMissingException("헤더 없음");
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer ", "");

            log.error("jwt:"+jwt);

            Claims claims = getAllClaims(jwt);
            if(isTokenExpired(claims)) { log.error("check"); }

            String id = (String) claims.get("id");
            String causer = (String) claims.get("cert");
            String org = (String) claims.get("org");
//            request.mutate().parameter()
            ServerHttpRequest modifiedRequest = exchange.getRequest()
                    .mutate()
                    .header("userid",id)
                    .header("causer", causer)
                    .header("org", org)
                    .build();


            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build();

            return chain.filter(exchange);
        };
    }

    /**
     * 토큰의 Claim 디코딩
     */
    private Claims getAllClaims(String token) {
        log.info("getAllClaims token = {}", token);
        log.info("check sigin key= {}", secretKey);
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰 만료기한 가져오기
     */
    public Date getExpirationDate(Claims claims) {

        return claims.getExpiration();
    }

    /**
     * 토큰이 만료되었는지
     */
    private boolean isTokenExpired(Claims claims) {
        return getExpirationDate(claims).before(new Date());
    }

}
