package com.youtube_timestamp.api.security;

import java.util.Arrays;
import java.util.List;

public class PermitWithNoTokenUrl {
    public static final List<String> url = Arrays.asList("/api/v1/auth/login", "/api/v1/auth/refresh_token", "/api/v1/health_check","/api/v1", "/api/v1/curation/pop");
}
