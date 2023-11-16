package com.trip.penguin.constant;

public class SecurityConstant {

    /**
     * Resource 대상
     */
    public static final String[] resourceArray = new String[] { "/css/**", "/fonts/**", "/images/**", "/js/**",
            "/modules/**", "/h2-console/**", "/swagger-ui/**" };

    /**
     * 권한제외 대상
     */
    public static final String[] permitAllArray = new String[]{"/login", "/home", "/signup", "/docs/index.html", "/actuator/**"};

    /**
     * 권한 인증 상대
     */
    public static final String[] authenticationAllArray = new String[] { "/**"};

}
