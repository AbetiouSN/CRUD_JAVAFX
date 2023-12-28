package com.employe.employe;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoEmployee {
    private Connection connection;

    public DaoEmployee() {
        connection = DaoFactury.getConnection();
    }
    public void addEmployee(Employee employee) {
        String query = "INSERT INTO Employee(NomEmp, Salaire, Age, RefDept) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, employee.getNomEmp());
            pst.setFloat(2, Float.parseFloat(employee.getSalaire()));
            pst.setInt(3, Integer.parseInt(employee.getAge()));
            pst.setInt(4, Integer.parseInt(employee.getRefDept()));

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Employee> getAllEmployees() {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Employee";
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Employee employee = new Employee(
                        new SimpleStringProperty(rs.getString("NomEmp")),
                        new SimpleStringProperty(rs.getString("Salaire")),
                        new SimpleStringProperty(rs.getString("Age")),
                        new SimpleStringProperty(rs.getString("RefDept"))
                );
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }


    public ObservableList<Employee> getAllEmployees1() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String query = "SELECT e.IdEmp, e.NomEmp, e.Salaire, e.Age, d.NomDept " +
                "FROM Employee e " +
                "JOIN Departement d ON e.RefDept = d.IdDept";
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setIdEmp(rs.getString("IdEmp"));
                emp.setNomEmp(rs.getString("NomEmp"));
                emp.setSalaire(rs.getString("Salaire"));
                emp.setAge(rs.getString("Age"));
                emp.setRefDept(rs.getString("NomDept"));
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }



    public int updateEmployee(int employeeId, String updatedName, float updatedSalaire, int updatedAge, String updatedRefDept) {
        int rowsUpdated = 0;
        String updateQuery = "UPDATE Employee SET NomEmp=?, Salaire=?, Age=?, RefDept=? WHERE IdEmp=?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, updatedName);
            updateStatement.setFloat(2, updatedSalaire);
            updateStatement.setInt(3, updatedAge);
            updateStatement.setString(4, updatedRefDept);
            updateStatement.setInt(5, employeeId);

            System.out.println("Update Query: " + updateStatement.toString());

            rowsUpdated = updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsUpdated;
    }
    public int deleteEmployeeById(int employeeId) {
        int rowsDeleted = 0;
        String query = "DELETE FROM Employee WHERE IdEmp=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, employeeId);
            rowsDeleted = pst.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsDeleted;
    }
    public ObservableList<Employee> searchEmployeesByDepartment(String departmentName) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String query = "SELECT e.IdEmp, e.NomEmp, e.Salaire, e.Age, d.NomDept " +
                "FROM Employee e " +
                "JOIN Departement d ON e.RefDept = d.IdDept " +
                "WHERE d.NomDept = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, departmentName);
            try (ResultSet rs = pst.executeQuery()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public ObservableList<Employee> searchEmployeesByName(String employeeName) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String query = "SELECT * FROM employee WHERE NomEmp = ?";

        /*SELECT e.IdEmp, e.NomEmp, e.Salaire, e.Age, d.NomDept  FROM FROM Employee e " +
        "JOIN Departement d ON e.RefDept = d.IdDept"+
        "WHERE NomEmp = ?";*/

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, employeeName);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setIdEmp(rs.getString("IdEmp"));
                    emp.setNomEmp(rs.getString("NomEmp"));
                    emp.setSalaire(rs.getString("Salaire"));
                    emp.setAge(rs.getString("Age"));
                    employees.add(emp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public ObservableList<Employee> searchEmployeesById(String employeeName) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String query = "SELECT * FROM employee WHERE IdEmp = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, employeeName);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setIdEmp(rs.getString("IdEmp"));
                    emp.setNomEmp(rs.getString("NomEmp"));
                    emp.setSalaire(rs.getString("Salaire"));
                    emp.setAge(rs.getString("Age"));
//                    emp.setRefDept(rs.getString("NomDept"));
                    employees.add(emp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }


    public String getLargestDepartment() {
        String largestDepartment = "N/A";
        String query = "SELECT d.NomDept, COUNT(e.IdEmp) as employeeCount " +
                "FROM Employee e " +
                "JOIN Departement d ON e.RefDept = d.IdDept " +
                "GROUP BY d.NomDept " +
                "ORDER BY employeeCount DESC " +
                "LIMIT 1";
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                largestDepartment = rs.getString("NomDept");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return largestDepartment;
    }

    public ObservableList<Departement> getDepartmentEmployeeCounts() {
        ObservableList<Departement> departements = FXCollections.observableArrayList();
        String query = "SELECT d.NomDept, COUNT(e.IdEmp) as employeeCount " +
                "FROM Employee e " +
                "JOIN Departement d ON e.RefDept = d.IdDept " +
                "GROUP BY d.NomDept";
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Departement departement = new Departement();
                departement.setDepartment(rs.getString("NomDept"));
                departement.setEmployeeCount(rs.getInt("employeeCount"));
                departements.add(departement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departements;
    }

    public float getTotalSalary() {
        float totalSalary = 0;
        String query = "SELECT SUM(Salaire) as totalSalary FROM Employee";
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                totalSalary = rs.getFloat("totalSalary");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalSalary;
    }

}
