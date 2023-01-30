package com.nnk.springboot.service;

import com.nnk.springboot.security.MyUserPrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;

@Service
public class LoginService {

    public String getGitHub (Principal user) {
        try {
            OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
            Map<String, Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();
            return userAttributes.get("login").toString();
        } catch (Exception e) {
            UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
            MyUserPrincipal u = (MyUserPrincipal) token.getPrincipal();
            return u.getUsername();
        }
    }

}
