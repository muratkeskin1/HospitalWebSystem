package com.muratkeskin.HospitalWebSystem.service.HospitalService;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.muratkeskin.HospitalWebSystem.model.Hospital;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class HospitalServiceImpl implements IHospitalService{


    public Hospital getHospital(String email) throws ExecutionException, InterruptedException {
        Firestore db=FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future=db.collection("hospital").whereEqualTo("hospitalEmail",email).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        Hospital hospital = null;
        for (DocumentSnapshot document : documents) {
            System.out.println(document.getId() + " => " + document.toObject(Hospital.class));
            if(hospital!=null){
                hospital.setHospitalId(document.getId());
            }
            hospital = document.toObject(Hospital.class);
        }
        return hospital;
    }
    @Override
    public boolean login(String hospitalEmail, String hospitalPassword) throws ExecutionException, InterruptedException {
         Firestore db=FirestoreClient.getFirestore();
        CollectionReference collectionReference=db.collection("hospital");
        Query query = collectionReference
                .whereEqualTo("hospitalEmail",hospitalEmail)
                .whereEqualTo("hospitalPassword",hospitalPassword);
        ApiFuture<QuerySnapshot> task=query.get();
        //ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot document = task.get();
        if (!document.isEmpty()) {
            return  true;
        } else {
            System.out.println("No such document!"+task);
            return false;
        }
    }
    @Override
    public UserDetails findByUserName(String hospitalName) throws ExecutionException, InterruptedException {
        Firestore db=  FirestoreClient.getFirestore();
        DocumentReference docRef= (DocumentReference) db.collection("hospital").whereEqualTo("hospitalName",hospitalName).get();
        ApiFuture<DocumentSnapshot> future=docRef.get();
        DocumentSnapshot documentSnapshot=future.get();
        Hospital hospital=null;
        if (documentSnapshot.exists()){
            hospital=documentSnapshot.toObject(Hospital.class);
            return new UserPrincipal(hospital);
        }
        else
            return null;
    }

}
