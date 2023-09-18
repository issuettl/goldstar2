package lgekorfrm.sso.emp;

import java.io.Serializable;

public class EmpUserProfileAccount implements Serializable {

    private String userID;
    private String userNo;
    private String userIDType;
    private String displayUserID;
    //private String userIDList;
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String country;
    private String countryName;
    private String subEmailAddr;
    private String blacklist;
    private String age;
    private String addr;
    private String roadAddr;
    private String city;
    private String usState;
    //private String serviceList;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserIDType() {
        return userIDType;
    }

    public void setUserIDType(String userIDType) {
        this.userIDType = userIDType;
    }

    public String getDisplayUserID() {
        return displayUserID;
    }

    public void setDisplayUserID(String displayUserID) {
        this.displayUserID = displayUserID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getSubEmailAddr() {
        return subEmailAddr;
    }

    public void setSubEmailAddr(String subEmailAddr) {
        this.subEmailAddr = subEmailAddr;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getRoadAddr() {
        return roadAddr;
    }

    public void setRoadAddr(String roadAddr) {
        this.roadAddr = roadAddr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUsState() {
        return usState;
    }

    public void setUsState(String usState) {
        this.usState = usState;
    }

    public String toString() {
        return "userID : " + userID + ", userNo : " + userNo + ", displayUserID : " + displayUserID + ", firstName : " + firstName + ", lastName : " + lastName + ", gender : " + gender;
    }
}
