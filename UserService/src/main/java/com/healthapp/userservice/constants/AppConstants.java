package com.healthapp.userservice.constants;

public class AppConstants {
    public static final String TOKEN_SECRET = "SM2P_GarbageCollectors";
    public static final long EXPIRATION_TIME = 864000000; //10 days

    // User Controller
    public static final String SIGN_IN = "/login";
    public static final String SIGN_UP = "/users/register";
    public static final String UPDATE_USER = "/users/update";
    public static final String DELETE_USER = "/users/delete/{userId}";
    public static final String GET_ALL_USERS = "/users/get-all";
    public static final String CHANGE_PASSWORD = "/users/change-password";
    public static final String ASSIGN_ROLE = "/users/assign-role/{userId}";
    public static final String REMOVE_ROLE = "/users/remove-role/{userId}";

    //Profile Controller
    public static final String CREATE_PROFILE = "/users/profile/create";
    public static final String UPDATE_PROFILE = "/users/profile/update";
    public static final String GET_PROFILE_BY_ID = "/users/profile/read-by-id/{userId}";
    public static final String GET_ALL_PROFILES = "/users/profile/get-all";

    //Contact Controller
    public static final String CREATE_CONTACT = "/users/contact/create";
    public static final String UPDATE_CONTACT = "/users/contact/update";
    public static final String GET_CONTACT_BY_ID = "/users/contact/read-by-id/{userId}";
    public static final String GET_ALL_CONTACTS = "/users/contact/get-all";

    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}

