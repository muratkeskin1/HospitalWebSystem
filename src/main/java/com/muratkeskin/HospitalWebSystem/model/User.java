package com.muratkeskin.HospitalWebSystem.model;

import com.google.cloud.firestore.GeoPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int userId;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    private long identificationNumber;
    private String bloodGroup;
    private String birthDate;
    private Address address;
    private Communication communication;
    private String deviceToken;
    private GeoPoint location;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Address{
    private int addressId;
    private String city;
    private String district;
    private String quarter;
    private String street;
    private int buildingNumber;
    private int apartmentNumber;
    private long userIdNo;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Communication{
    private int communicationId;
    private String email;
    private long phoneNumber;
    private long userIdNo;
}