package com.employe.employe;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;

public class HelloController implements Initializable{
    private int selectedEmployeeId;
    @FXML
    private Label lblLargestDepartment;
    @FXML
    private Label welcomeText;
    @FXML
    private Label lblTotalSalary;
    @FXML
    private TableView<Departement> departmentEmployeeTable;
    @FXML
    private TableColumn<Departement, String> departmentColumn;
    @FXML
    private TableColumn<Departement, Integer> employeeCountColumn;
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
    private ChoiceBox<String> dropItems;

    private  String[] srch = {"Departement","Nom Employe","ID"};

    @FXML
    void Add(ActionEvent event) {
        Employee newEmployee = new Employee(
                new SimpleStringProperty(txtName.getText()),
                new SimpleStringProperty(txtSalaire.getText()),
                new SimpleStringProperty(txtAge.getText()),
                new SimpleStringProperty(txtDepart.getText())
        );
        DaoEmployee daoEmployee = new DaoEmployee();
        daoEmployee.addEmployee(newEmployee);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Test Connection !!");

            alert.setHeaderText("Register Employe !!");
            alert.setContentText("Employe Added !!");
            alert.showAndWait();

            table();
            updateLargestDepartment();
            updateDepartmentEmployeeTable();
            updateTotalSalary();
    }
    void table() {
        DaoEmployee daoEmployee = new DaoEmployee();
        ObservableList<Employee> employees = daoEmployee.getAllEmployees1();

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

        lblTotalEmployees.setText("Total Employees: " + employees.size());
    }

    @FXML
    void Delete(ActionEvent event) {
        if (selectedEmployeeId > 0) {
            DaoEmployee daoEmployee = new DaoEmployee();
            int rowsDeleted = daoEmployee.deleteEmployeeById(selectedEmployeeId);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Employee");
            alert.setHeaderText(null);

            if (rowsDeleted > 0) {
                alert.setContentText("Employee Deleted");
                table();
                updateLargestDepartment();
                updateDepartmentEmployeeTable();
                updateTotalSalary();
            } else {
                alert.setContentText("No employee deleted. ID not found");
            }
            alert.showAndWait();
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
            float updatedSalaire = Float.parseFloat(txtSalaire.getText());
            int updatedAge = Integer.parseInt(txtAge.getText());
            String updatedRefDept = txtDepart.getText();

//            System.out.println("Selected Employee ID: " + selectedEmployeeId);
//            System.out.println("Updated Name: " + updatedName);
//            System.out.println("Updated Salaire: " + updatedSalaire);
//            System.out.println("Updated Age: " + updatedAge);
//            System.out.println("Updated RefDept: " + updatedRefDept);

            DaoEmployee daoEmployee = new DaoEmployee();
            int rowsUpdated = daoEmployee.updateEmployee(selectedEmployeeId, updatedName, updatedSalaire, updatedAge, updatedRefDept);
            System.out.println("Rows updated: " + rowsUpdated);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Employee");
            alert.setHeaderText(null);
            if (rowsUpdated > 0) {
                alert.setContentText("Employee Updated!");
                table();
                updateDepartmentEmployeeTable();
                updateTotalSalary();
            } else {
                alert.setContentText("No employee updated. ID not found");
            }

            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Employee");
            alert.setHeaderText(null);
            alert.setContentText("Please select an employee to update.");
            alert.showAndWait();
        }
    }


//@FXML
//void Search(ActionEvent event) {
//    String departementName = txtSearch.getText();
//    if (!departementName.isEmpty()) {
//        DaoEmployee daoEmployee = new DaoEmployee();
//        ObservableList<Employee> employees = daoEmployee.searchEmployeesByDepartment(departementName);
//        table.setItems(employees);
//    } else {
//        table();
//    }
//}

    @FXML
    void Search(ActionEvent event) {
        String departementName = txtSearch.getText();
        if (!departementName.isEmpty()) {
            DaoEmployee daoEmployee = new DaoEmployee();
            if (dropItems.getValue().equals("Departement")) {
                ObservableList<Employee> employees = daoEmployee.searchEmployeesByDepartment(departementName);
                table.setItems(employees);
            } else if (dropItems.getValue().equals("Nom Employe")) {
                ObservableList<Employee> employees = daoEmployee.searchEmployeesByName(departementName);
                table.setItems(employees);
            }else{
                ObservableList<Employee> employees = daoEmployee.searchEmployeesById(departementName);
                table.setItems(employees);
            }
        } else {
            table();
        }
    }




    void updateLargestDepartment() {
        System.out.println("Updating largest department...");
        DaoEmployee daoEmployee = new DaoEmployee();
        String largestDepartment = daoEmployee.getLargestDepartment();
        lblLargestDepartment.setText("Largest Department: " + largestDepartment);
    }



    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    void updateDepartmentEmployeeTable() {
        DaoEmployee daoEmployee = new DaoEmployee();
        ObservableList<Departement> departmentEmployeeCounts = daoEmployee.getDepartmentEmployeeCounts();
        System.out.println("Departements: " + departmentEmployeeCounts);
        departmentEmployeeTable.setItems(departmentEmployeeCounts);
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        employeeCountColumn.setCellValueFactory(new PropertyValueFactory<>("employeeCount"));
    }

    void updateTotalSalary() {
        DaoEmployee daoEmployee = new DaoEmployee();
        float totalSalary = daoEmployee.getTotalSalary();
        lblTotalSalary.setText("Total Salary: " + totalSalary);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing...");
        DaoFactury.getConnection();
        table();
        updateLargestDepartment();
        updateDepartmentEmployeeTable();
        updateTotalSalary();
        dropItems.getItems().addAll(srch);

    }
}

