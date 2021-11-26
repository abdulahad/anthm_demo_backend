package com.cirruslabs.anthm.project.demo.employee.model;

public class EmployeeSearchCriteria {


    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    private String keywords;
    private String firstName;
    private String lastName;
    private String designation;

    public String getDesignation() {
        return designation;
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

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
