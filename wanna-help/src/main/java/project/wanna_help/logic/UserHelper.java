package project.wanna_help.logic;

import org.springframework.security.core.context.SecurityContextHolder;
import project.wanna_help.persistence.domain.AppUser;
import project.wanna_help.security.UserPrincipal;

public class UserHelper {



    /**
     * get current User from the cookies, after Signin, now it's possible to
     * get Data depending from the currently locked-in User and it's privileges
     * @return AppUser
     */
    public static AppUser getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getAppUser();
    }
}
