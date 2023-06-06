package com.muratkeskin.HospitalWebSystem.service.HospitalService;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.muratkeskin.HospitalWebSystem.model.Hospital;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class HospitalDetailService implements UserDetailsService {
    private Firestore db= FirestoreClient.getFirestore();

    public HospitalDetailService(HospitalDetailService hospitalService) {
    }

    public HospitalDetailService() {
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String hospitalName) throws UsernameNotFoundException {
        Firestore db=  FirestoreClient.getFirestore();
        DocumentReference docRef= (DocumentReference) db.collection("hospital").whereEqualTo("hospitalName","hastane1").get();
        ApiFuture<DocumentSnapshot> future=docRef.get();
        DocumentSnapshot documentSnapshot=future.get();
        Hospital hospital=null;
        if (documentSnapshot.exists()){
            hospital=documentSnapshot.toObject(Hospital.class);
            final List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            hospital=documentSnapshot.toObject(Hospital.class);
            return new UserPrincipal(hospital);
        }
        else
            throw new UsernameNotFoundException("username not found");
    }
    private UserDetails buildUserForAuthentication(Hospital hospital, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(hospital.getHospitalName(), hospital.getHospitalPassword(), authorities);
    }
}
