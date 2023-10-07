package com.healthapp.recommendationservicemanual.utilities.constants;

/**
 * TokenConstants provides constants related to authentication tokens.
 */
public class TokenConstants {
    // The secret key used for token generation and validation.
    public static final String TOKEN_SECRET = "SM2P_GarbageCollectors";

    // The expiration time for authentication tokens in milliseconds (7 days).
    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days

    // The HTTP header name for the authentication token.
    public static final String HEADER_STRING = "Authorization";

    // The prefix for authentication tokens in HTTP headers.
    public static final String TOKEN_PREFIX = "Bearer ";

    // The maximum number of login attempts allowed.
    public static final Integer MAX_LOGIN_ATTEMPTS_LIMIT = 3;

    // A string containing all alphanumeric characters (digits, uppercase and lowercase letters).
    public static final String ALPHABETS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
}
