package com.tongwii.ico.security;

import com.tongwii.ico.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long   serialVersionUID   = - 3301605591108950415L;
    /** 用户名key **/
    private static final String CLAIM_KEY_USERNAME = "sub";
    /** 用户模型的用户代理(终端)key **/
    private static final String CLAIM_KEY_AUDIENCE = "audience";
        /** token创建时间key **/
        private static final String CLAIM_KEY_CREATED  = "created";


    /** 签名密钥 **/
    @Value( "${jwt.secret}" )
    private String  secret;
    /** token过期时间 **/
    @Value( "${jwt.expiration}" )
    private Integer expiration;
    /** header key **/
    @Value( "${jwt.header}" )
    private String  tokenHeaderKey;

    /**
     * 根据token得到用户名
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken ( String token ) {
        String username;
        try {
            final Claims claims = getClaimsFromToken( token );
            username = claims.getSubject();
        } catch ( Exception e ) {
            username = null;
        }
        return username;
    }

    /**
     * 得到token的开始时间(或者说创建时间)
     *
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken ( String token ) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken( token );
            created = new Date( ( Long ) claims.get( CLAIM_KEY_CREATED ) );
        } catch ( Exception e ) {
            created = null;
        }
        return created;
    }

    /**
     * 得到token过期时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken ( String token ) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken( token );
            expiration = claims.getExpiration();
        } catch ( Exception e ) {
            expiration = null;
        }
        return expiration;
    }


    /**
     * @param token
     * @return
     */
    private Claims getClaimsFromToken ( String token ) {
        Claims claims;
        try {
            claims = Jwts.parser()
                         .setSigningKey( secret ) // 签名密钥
                         .parseClaimsJws( token ) // token
                         .getBody();
        } catch ( Exception e ) {
            claims = null;
        }
        return claims;
    }


    /**
     * token是否过期
     *
     * @param token
     * @return expiration > 当前时间
     */
    private Boolean isTokenExpired ( String token ) {
        final Date expiration = getExpirationDateFromToken( token );
        return expiration.before( new Date() );
    }


    /**
     * 构建token
     *
     * @param userDetails
     * @return
     */
    public String generateToken ( UserDetails userDetails ) {
        Map< String, Object > claims = new HashMap<>();
        claims.put( CLAIM_KEY_USERNAME, userDetails.getUsername() );
        claims.put( CLAIM_KEY_CREATED, new Date() );
        return generateToken( claims );
    }

    /**
     * 构建token
     *
     * @param user the user
     * @return string
     */
    public String generateToken ( User user ) {
        Map< String, Object > claims = new HashMap<>();
        claims.put( CLAIM_KEY_USERNAME, user.getEmailAccount() );
        claims.put( CLAIM_KEY_CREATED, new Date() );
        return generateToken( claims );
    }

    /**
     * 构建token
     *
     * @param claims
     * @return
     */
    String generateToken ( Map< String, Object > claims ) {
        return Jwts.builder()
                   .setClaims( claims )
                   .setExpiration(generateExpirationDate(new Date(), expiration))
                   .signWith( SignatureAlgorithm.HS512, secret )
                   .compact();
    }

    /**
     * 生成过期时间，在当前时间基础上+秒
     * @param date 基于时间
     * @param number 单位：秒
     * @return
     */
    public Date generateExpirationDate(Date date, int number) {
        Calendar instance = Calendar.getInstance();
        instance.setTime( date );
        instance.add( Calendar.SECOND, number );
        return instance.getTime();
    }

    /**
     * token是否可以刷新
     *
     * @param token
     * @return
     */
    public Boolean canTokenBeRefreshed ( String token) {
        return !isTokenExpired( token );
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken ( String token ) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken( token );
            claims.put( CLAIM_KEY_CREATED, new Date() );
            refreshedToken = generateToken( claims );
        } catch ( Exception e ) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证token
     *
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken ( String token, UserDetails userDetails ) {
        JwtUser user = (JwtUser) userDetails;
        final String username = this.getUsernameFromToken( token );
        final Date   created  = this.getCreatedDateFromToken( token );
        return ( username.equals( user.getUsername() ) // 用户名校验 
                && ! isTokenExpired( token )           // token有效期校验
        );
    }
}