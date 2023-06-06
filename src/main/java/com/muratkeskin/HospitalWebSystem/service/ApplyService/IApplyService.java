package com.muratkeskin.HospitalWebSystem.service.ApplyService;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.muratkeskin.HospitalWebSystem.model.Apply;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IApplyService {
    void save(Apply apply) throws FirebaseMessagingException, ExecutionException, InterruptedException, Exception;

    void delete(String id) throws ExecutionException, InterruptedException;
    //List<Apply> getAll();
}
