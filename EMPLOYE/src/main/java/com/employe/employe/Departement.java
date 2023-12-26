package com.employe.employe;

public class Departement {

    private String department;
    private int employeeCount;

    public Departement(String department, int employeeCount) {
        this.department = department;
        this.employeeCount = employeeCount;
    }

    public Departement() {
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }
}
