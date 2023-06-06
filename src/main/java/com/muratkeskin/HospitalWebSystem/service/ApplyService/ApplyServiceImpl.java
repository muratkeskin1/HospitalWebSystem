package com.muratkeskin.HospitalWebSystem.service.ApplyService;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.*;
import com.google.protobuf.Api;
import com.muratkeskin.HospitalWebSystem.model.Apply;
import com.muratkeskin.HospitalWebSystem.model.Donor;
import com.muratkeskin.HospitalWebSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.thymeleaf.processor.comment.ICommentStructureHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ApplyServiceImpl implements IApplyService {

    private final FireMessage fireMessage=new FireMessage("test","test");

    public ApplyServiceImpl() throws JSONException {
    }
    @Override
    public void save(Apply apply) throws Exception {
        apply.setCreatedAt(Calendar.getInstance().getTime());
        Firestore db= FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> docref=db.collection("apply").add(apply);
        Thread.sleep(200);
        ApiFuture<QuerySnapshot> future1=db.collection("apply")
                .whereEqualTo("bloodGroup",apply.getBloodGroup())
                .whereEqualTo("complete",false)
                .whereEqualTo("countOfNeeds",apply.getCountOfNeeds())
                .whereEqualTo("createdAt",apply.getCreatedAt())
                .whereEqualTo("description",apply.getDescription())
                .whereEqualTo("hospitalEmail",apply.getHospitalEmail())
                .whereEqualTo("hospitalName",apply.getHospitalName())
                .whereEqualTo("location",apply.getLocation())
                .whereEqualTo("patientIdNo",apply.getPatientIdNo())
                .whereEqualTo("patientName",apply.getPatientName())
                .get();
        String docId="";
        Thread.sleep(100);
        List<QueryDocumentSnapshot> docs=future1.get().getDocuments();
        List<Apply> listApply=new ArrayList<Apply>();
        for (DocumentSnapshot doc:docs){
            System.out.println(doc);
            docId=doc.getId();
            System.out.println(doc.getId());
            listApply.add(doc.toObject(Apply.class));
        }
        sendNotifications(apply.getHospitalName(),apply.getBloodGroup(),apply.getCountOfNeeds(),apply,docId);
    }

    @Override
    public void delete(String id) throws ExecutionException, InterruptedException {
        Firestore db= FirestoreClient.getFirestore();
        DocumentReference documentReference=db.collection("apply").document(id);
        ApiFuture<WriteResult> future=documentReference.update("complete",true);
  /*      ApiFuture<DocumentSnapshot> future=documentReference.get();
        DocumentSnapshot documentSnapshot=future.get();
        Apply apply=null;
        if(documentSnapshot.exists()){
            apply=documentSnapshot.toObject(Apply.class);
            System.out.println(apply);
            apply.setComplete(true);

        }
*/
    }
    public void sendNotifications(String hospitalName,String bloodGroup,int count,Apply apply,String  docId) throws Exception {
        Firestore db= FirestoreClient.getFirestore();
        db= FirestoreClient.getFirestore();
        System.out.println(apply.getBloodGroup());
        System.out.println(apply.getLocation());
            ApiFuture<QuerySnapshot> future1=db.collection("users")
                    .whereEqualTo("address.city",apply.getLocation())
                    .whereEqualTo("bloodGroup",apply.getBloodGroup())
                .get();
        List<QueryDocumentSnapshot> docs=future1.get().getDocuments();
        List<User> listUser=new ArrayList<User>();
        for (DocumentSnapshot doc:docs){
            System.out.println(doc);
            User user = doc.toObject(User.class);
            if(user!=null){
               if((6371*Math.acos(Math.cos(Math.toRadians(apply.getGeoPoint().getLatitude()))
                       *Math.cos(Math.toRadians(user.getLocation().getLatitude()))
               * Math.cos(Math.toRadians(Math.toRadians(user.getLocation().getLongitude()))
                       -Math.toRadians(apply.getGeoPoint().getLongitude()))
                       +Math.sin(Math.toRadians(apply.getGeoPoint().getLatitude()))
                       *Math.sin(Math.toRadians(user.getLocation().getLatitude()))))<20)
                //3959 * acos( cos( radians(37) ) * cos( radians( lat ) ) * cos( radians( lng ) - radians(-122) )
                // + sin( radians(37) ) * sin( radians( lat ) ) )
                System.out.println(user.getLocation().getLatitude());
                System.out.println(user.getLocation().getLongitude());
                System.out.println(doc.getData());
                listUser.add(doc.toObject(User.class));
            }
        }
        System.out.println("listenin boyu "+ listUser.size()+" ");
        Thread.sleep(100);
        List<Donor> donorList=new ArrayList<Donor>();
        List<String> tokenList=new ArrayList<String>();
        for (int i=0;i<listUser.size();i++){
            if(listUser.get(i)!=null){
         Donor donor=new Donor();
         donor.setComplete(false);
         donor.setApplyId(docId);
         donor.setCreatedAt(Calendar.getInstance().getTime());
         donor.setDonorIdNo(listUser.get(i).getIdentificationNumber());
         donor.setHospitalName(hospitalName);
         donor.setBloodGroup(bloodGroup);
         donor.setPatientName(apply.getPatientName());
         donor.setSendNotification(true);
         tokenList.add(listUser.get(i).getDeviceToken());
                db.collection("apply").document(docId).collection("donors").add(donor);
                db.collection("donors").add(donor);
         donorList.add(donor);}
        }
        for(int i=0;i<tokenList.size();i++){
            Message message= Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle("kan bağışı")
                            .setBody("kan bağışı"+ apply.getHospitalName())
                            .build())
                    .setAndroidConfig(AndroidConfig.builder()
                            .setTtl(2600*1000)
                            .setNotification(AndroidNotification.builder()
                                    .setColor("#f43528").build()).build())
                    .setApnsConfig(ApnsConfig.builder()
                            .setAps(Aps.builder()
                                    .setBadge(42)
                                    .build()).build())
                    .setToken(tokenList.get(i))
                    .build();
            FirebaseMessaging.getInstance().send(message);
        }
    }
    public  List<Apply> getAll(String hospitalEmail) throws ExecutionException, InterruptedException {
        Firestore db= FirestoreClient.getFirestore();
       ApiFuture<QuerySnapshot> future1=db.collection("apply").whereEqualTo("hospitalEmail",hospitalEmail).whereEqualTo("complete",false).get();
       List<QueryDocumentSnapshot> docs=future1.get().getDocuments();
       List<Apply> listApply=new ArrayList<Apply>();
       for (DocumentSnapshot doc:docs){
           System.out.println(doc);
           Apply apply=doc.toObject(Apply.class);
           assert apply != null;
           apply.setApplyId(doc.getId());
           listApply.add(apply);
       }
       return listApply;
    }
}
