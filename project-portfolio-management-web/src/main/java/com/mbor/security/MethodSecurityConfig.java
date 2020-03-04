package com.mbor.security;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        proxyTargetClass=true
)
public class MethodSecurityConfig  extends GlobalMethodSecurityConfiguration {
}
