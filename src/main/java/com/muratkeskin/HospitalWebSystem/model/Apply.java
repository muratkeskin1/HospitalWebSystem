package com.muratkeskin.HospitalWebSystem.model;

import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Apply {
    @DocumentId
    private String applyId=null;
    private long patientIdNo;
    private String patientName;
    private String bloodGroup;
    private String hospitalName;
    private String hospitalEmail;
    private String description;
    private String location;
    private int countOfNeeds;
    private Date createdAt;
    private GeoPoint geoPoint;
    private boolean isComplete;
}
