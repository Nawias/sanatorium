package com.sanatorium.sanatorium.security;

import com.sanatorium.sanatorium.models.User;
import com.sanatorium.sanatorium.repo.PermissionRepo;
import com.sanatorium.sanatorium.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map attributes = oidcUser.getAttributes();
        GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo();
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setId((String) attributes.get("sub"));
        userInfo.setName((String) attributes.get("name"));
        System.out.println("loadUser(): userInfo: "+ userInfo.toString());
        updateUser(userInfo);
        return oidcUser;
    }

    private void updateUser(GoogleOAuth2UserInfo userInfo) {

        System.out.println("updateUser(): userInfo: "+ userInfo.toString());
        User user = userRepo.findUserByEmail(userInfo.getEmail());
        if(user == null) {
            user = new User();
            user.setPermission(permissionRepo.findPermissionByName("patient"));
        }
        user.setEmail(userInfo.getEmail());
        user.setName(userInfo.getName());

        userRepo.save(user);
    }
}
