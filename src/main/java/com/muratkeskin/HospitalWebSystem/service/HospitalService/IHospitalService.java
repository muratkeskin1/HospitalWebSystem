package com.muratkeskin.HospitalWebSystem.service.HospitalService;

import com.muratkeskin.HospitalWebSystem.model.Hospital;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.concurrent.ExecutionException;

public interface IHospitalService {
    boolean login(String hospitalName,String hospitalPassword) throws ExecutionException, InterruptedException;
    UserDetails findByUserName(String hospitalName) throws ExecutionException, InterruptedException;
}
