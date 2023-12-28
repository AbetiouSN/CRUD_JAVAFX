package com.employe.employe;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

@Data
@Builder
public class Employee {
    @Generated
    private StringProperty idEmp;
    private StringProperty  nomEmp;
    private StringProperty  salaire;
    private StringProperty  age;
    private StringProperty  refDept;




    public Employee(StringProperty idEmp, StringProperty nomEmp, StringProperty salaire, StringProperty age, StringProperty refDept) {
        this.idEmp = idEmp;
        this.nomEmp = nomEmp;
        this.salaire = salaire;
        this.age = age;
        this.refDept = refDept;
    }
    public Employee(StringProperty nomEmp, StringProperty salaire, StringProperty age, StringProperty refDept) {
        this.idEmp = idEmp;
        this.nomEmp = nomEmp;
        this.salaire = salaire;
        this.age = age;
        this.refDept = refDept;
    }

    public Employee() {
        idEmp = new SimpleStringProperty(this,"idEmp");
        nomEmp = new SimpleStringProperty(this,"nomEmp");
        salaire = new SimpleStringProperty(this,"salaire");
        age = new SimpleStringProperty(this,"age");
        refDept = new SimpleStringProperty(this,"refDept");
    }

    public StringProperty idProperty(){return idEmp;}
    public StringProperty nameProperty(){return nomEmp;}
    public StringProperty salaireProperty(){return salaire;}
    public StringProperty ageProperty(){return age;}
    public StringProperty departementProperty(){return refDept;}

    public String getIdEmp() {
        return idEmp.get();
    }

    public StringProperty idEmpProperty() {
        return idEmp;
    }

    public void setIdEmp(String idEmp) {
        this.idEmp.set(idEmp);
    }


    public StringProperty nomEmpProperty() {
        return nomEmp;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp.set(nomEmp);
    }

    public String getSalaire() {
        return salaire.get();
    }
    public String getNomEmp() {
        return nomEmp.get();
    }


    public void setSalaire(String salaire) {
        this.salaire.set(salaire);
    }

    public String getAge() {
        return age.get();
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public String getRefDept() {
        return refDept.get();
    }

    public StringProperty refDeptProperty() {
        return refDept;
    }

    public void setRefDept(String refDept) {
        this.refDept.set(refDept);
    }
}
