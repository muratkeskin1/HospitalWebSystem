package com.muratkeskin.HospitalWebSystem.configuration;

import com.muratkeskin.HospitalWebSystem.model.Hospital;
import com.muratkeskin.HospitalWebSystem.service.HospitalService.HospitalDetailService;
import com.muratkeskin.HospitalWebSystem.service.HospitalService.HospitalServiceImpl;
import com.muratkeskin.HospitalWebSystem.service.HospitalService.UserPrincipal;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
    @Autowired
    private HospitalServiceImpl hospitalService;

    @SneakyThrows
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String refererUrl = request.getHeader("Referer");
        System.out.println(refererUrl);
        if(authentication!=null)
        {
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        String username=userDetails.getUsername();}
        super.onLogoutSuccess(request, response, authentication);
    }
}
