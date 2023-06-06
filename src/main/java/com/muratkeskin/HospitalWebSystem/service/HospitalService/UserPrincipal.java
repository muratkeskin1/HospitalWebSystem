package com.muratkeskin.HospitalWebSystem.service.HospitalService;

import com.muratkeskin.HospitalWebSystem.model.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
public class UserPrincipal implements UserDetails {
    private Hospital hospital;
    public UserPrincipal(Hospital hospital){
        this.hospital=hospital;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return hospital.getHospitalPassword();
    }

    @Override
    public String getUsername() {
        return hospital.getHospitalName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.hospital.getActive()==1;
    }
}
