package com.muratkeskin.HospitalWebSystem.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @DocumentId
    private String patientId=null;
}
