package com.employe.employe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.Label;


import javafx.event.ActionEvent;

//import java.awt.Label;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;

public class HelloController implements Initializable{
    private int selectedEmployeeId;

    @FXML
    private Label welcomeText;



    @FXML
    private TableColumn<Employee, String> AgeColomn;

    @FXML
    private TableColumn<Employee, String>  DepartementColomn;

    @FXML
    private TableColumn<Employee, String>  IdColomn;

    @FXML
    private TableColumn<Employee, String>  NameColomn;

    @FXML
    private TableColumn<Employee, String>  SalaireColomn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableView<Employee> table;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtDepart;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalaire;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label lblTotalEmployees;

    @FXML
    void Add(ActionEvent event) {
        String nomEmp;
        int age;
        float salaire;
        int RefDept;
        Connection MyCon = DaoFactury.getConnection();
        nomEmp = txtName.getText();
        age = Integer.parseInt(txtAge.getText());
        salaire = Float.parseFloat(txtSalaire.getText());
        RefDept = Integer.parseInt(txtDepart.getText());



        String Requete;
        Requete = "INSERT INTO Employee(NomEmp, Salaire, Age, RefDept) VALUES( ?, ?, ?, ?)";
        PreparedStatement pst;
        try {
            pst = MyCon.prepareStatement(Requete);
//            pst.setInt(1,nomEmp);
            pst.setString(1, nomEmp);
            pst.setFloat(2,salaire);
            pst.setInt(3,age);
            pst.setInt(4,RefDept);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test Connection !!");

            alert.setHeaderText("Register Employe !!");
            alert.setContentText("Employe Added !!");
            alert.showAndWait();

            table();
            updateLargestDepartment();
            updateDepartmentEmployeeTable();
            updateTotalSalary();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


void table() {
    Connection MyCon = DaoFactury.getConnection();

    ObservableList<Employee> employees = FXCollections.observableArrayList();
    PreparedStatement pst;

    try {
        pst = MyCon.prepareStatement("SELECT e.IdEmp, e.NomEmp, e.Salaire, e.Age, d.NomDept " +
                "FROM Employee e " +
                "JOIN Departement d ON e.RefDept = d.IdDept;");
        ResultSet rs = pst.executeQuery();
        {
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setIdEmp(rs.getString("IdEmp"));
                emp.setNomEmp(rs.getString("NomEmp"));
                emp.setSalaire(rs.getString("Salaire"));
                emp.setAge(rs.getString("Age"));
                emp.setRefDept(rs.getString("NomDept"));
                employees.add(emp);
            }
        }
        table.setItems(employees);
        IdColomn.setCellValueFactory(f -> f.getValue().idProperty());
        NameColomn.setCellValueFactory(f -> f.getValue().nameProperty());
        SalaireColomn.setCellValueFactory(f -> f.getValue().salaireProperty());
        AgeColomn.setCellValueFactory(f -> f.getValue().ageProperty());
        DepartementColomn.setCellValueFactory(f -> f.getValue().departementProperty());

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && (!table.getSelectionModel().isEmpty())) {
                int myIndex = table.getSelectionModel().getSelectedIndex();
                selectedEmployeeId = Integer.parseInt(table.getItems().get(myIndex).getIdEmp());
                System.out.println("Selected Employee ID: " + selectedEmployeeId);

                txtName.setText(table.getItems().get(myIndex).getNomEmp());
                txtSalaire.setText(String.valueOf(table.getItems().get(myIndex).getSalaire()));
                txtAge.setText(String.valueOf(table.getItems().get(myIndex).getAge()));
                txtDepart.setText(String.valueOf(table.getItems().get(myIndex).getRefDept()));
            }
        });
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    lblTotalEmployees.setText("Total Employees: " + employees.size());
}


    @FXML
    void Delete(ActionEvent event) {
        if (selectedEmployeeId > 0) {
            Connection MyCon = DaoFactury.getConnection();
            String Requete = "DELETE FROM Employee WHERE IdEmp=?";
            PreparedStatement pst;

            try {
                pst = MyCon.prepareStatement(Requete);
                pst.setInt(1, selectedEmployeeId);

                int rowsDeleted = pst.executeUpdate();
                System.out.println("Rows deleted: " + rowsDeleted);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Employee");
                alert.setHeaderText(null);

                if (rowsDeleted > 0) {
                    alert.setContentText("Employee Deleted!");
                    table();
                    updateLargestDepartment();
                    updateDepartmentEmployeeTable();
                    updateTotalSalary();


                } else {
                    alert.setContentText("No employee deleted. ID not found?");
                }

                alert.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Employee");
            alert.setHeaderText(null);
            alert.setContentText("Please select an employee to delete.");

            alert.showAndWait();
        }
    }



    @FXML
    void Update(ActionEvent event) {
        if (selectedEmployeeId > 0) {
            String updatedName = txtName.getText();

            Connection MyCon = DaoFactury.getConnection();
            String updateQuery = "UPDATE Employee SET NomEmp=? WHERE IdEmp=?";
            try {
                PreparedStatement updateStatement = MyCon.prepareStatement(updateQuery);
                updateStatement.setString(1, updatedName);
                updateStatement.setInt(2, selectedEmployeeId);

                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Update Employee");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee Updated!");
                    alert.showAndWait();

                    table();
                    updateTotalSalary();


                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Update Employee");
                    alert.setHeaderText(null);
                    alert.setContentText("No employee updated. ID not found?");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Employee");
            alert.setHeaderText(null);
            alert.setContentText("Please select an employee to update.");
            alert.showAndWait();
        }
    }






    @FXML
    void Search(ActionEvent event) {

        String departementName = txtSearch.getText();
        if (!departementName.isEmpty()) {
            Connection MyCon = DaoFactury.getConnection();
            String Requete = "SELECT e.IdEmp, e.NomEmp, e.Salaire, e.Age, d.NomDept " +
                    "FROM Employee e " +
                    "JOIN Departement d ON e.RefDept = d.IdDept " +
                    "WHERE d.NomDept = ?";
            PreparedStatement pst;

            try {
                pst = MyCon.prepareStatement(Requete);
                pst.setString(1, departementName);
                ResultSet rs = pst.executeQuery();

                ObservableList<Employee> employees = FXCollections.observableArrayList();

                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setIdEmp(rs.getString("IdEmp"));
                    emp.setNomEmp(rs.getString("NomEmp"));
                    emp.setSalaire(rs.getString("Salaire"));
                    emp.setAge(rs.getString("Age"));
                    emp.setRefDept(rs.getString("NomDept"));
                    employees.add(emp);
                }

                table.setItems(employees);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Handle the case when the search field is empty
            // You can show an alert or reset the table to display all employees, for example.
            table();
        }
    }


    @FXML
    private Label lblLargestDepartment;

    void updateLargestDepartment() {
        System.out.println("Updating largest department...");
        Connection MyCon = DaoFactury.getConnection();
        String query = "SELECT d.NomDept, COUNT(e.IdEmp) as employeeCount " +
                "FROM Employee e " +
                "JOIN Departement d ON e.RefDept = d.IdDept " +
                "GROUP BY d.NomDept " +
                "ORDER BY employeeCount DESC " +
                "LIMIT 1";

        try {
            PreparedStatement pst = MyCon.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String largestDepartment = rs.getString("NomDept");
                lblLargestDepartment.setText("Largest Department: " + largestDepartment);
            } else {
                lblLargestDepartment.setText("Largest Department: N/A");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    @FXML
    private TableView<Departement> departmentEmployeeTable;

    @FXML
    private TableColumn<Departement, String> departmentColumn;

    @FXML
    private TableColumn<Departement, Integer> employeeCountColumn;

    void updateDepartmentEmployeeTable() {
        Connection MyCon = DaoFactury.getConnection();
        String query = "SELECT d.NomDept, COUNT(e.IdEmp) as employeeCount " +
                "FROM Employee e " +
                "JOIN Departement d ON e.RefDept = d.IdDept " +
                "GROUP BY d.NomDept";

        ObservableList<Departement> departements = FXCollections.observableArrayList();

        try {
            PreparedStatement pst = MyCon.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Departement departement = new Departement();
                departement.setDepartment(rs.getString("NomDept"));
                departement.setEmployeeCount(rs.getInt("employeeCount"));
                departements.add(departement);
            }

            System.out.println("Departements: " + departements);

            departmentEmployeeTable.setItems(departements);
            departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
            employeeCountColumn.setCellValueFactory(new PropertyValueFactory<>("employeeCount"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Label lblTotalSalary;

    void updateTotalSalary() {
        Connection MyCon = DaoFactury.getConnection();
        String query = "SELECT SUM(Salaire) as totalSalary FROM Employee";

        try {
            PreparedStatement pst = MyCon.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String totalSalary = rs.getString("totalSalary");
                lblTotalSalary.setText("Total Salary: " + totalSalary);
            } else {
                lblTotalSalary.setText("Total Salary: N/A");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing...");
        DaoFactury.getConnection();
        table();
        updateLargestDepartment();
        updateDepartmentEmployeeTable();
        updateTotalSalary();

    }
}