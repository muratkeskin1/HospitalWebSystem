package com.muratkeskin.HospitalWebSystem.model;

import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {
   @DocumentId
    private String hospitalId=null;
    private String hospitalEmail;
    private String hospitalName;
    private String hospitalPassword;
    private String hospitalAddress;
    private String hospitalPhone;
    private GeoPoint location;
    private int active;
   //? private String hospitalLocation;

}
