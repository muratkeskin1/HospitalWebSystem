package com.muratkeskin.HospitalWebSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Donor {
    private String applyId;
    private long donorIdNo;
    private boolean complete;
    private String patientName;
    private String hospitalName;
    private String bloodGroup;
    private Date createdAt;
    private boolean sendNotification=false;
}
