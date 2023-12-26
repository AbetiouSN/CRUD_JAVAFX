//package com.employe.employe;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class DaoEmployee implements CRUD<Employee, Integer> {
//    Connection MyCon = DaoFactury.getConnection();
//
//    @Override
//    public boolean Create(Employee emp) {
//        String Requete;
//        Requete = "INSERT INTO Employee(IdEmp, NomEmp, Salaire, Age, RefDept) VALUES(?, ?, ?, ?, ?)";
//        PreparedStatement pst;
//        try {
//            pst = MyCon.prepareStatement(Requete);
//            pst.setInt(1, emp.getIdEmp());
//            pst.setString(2, emp.getNomEmp());
//            pst.setFloat(3, emp.getSalaire());
//            pst.setInt(4, emp.getAge());
//            pst.setInt(5, emp.getRefDept());
//
//            int Lig = pst.executeUpdate();
//            if (Lig == 1)
//                System.out.println("Employee added with success");
//            else
//                System.out.println("Employee added failed !!!");
//            return true;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }
//
//    @Override
//    public List<Employee> all() {
//        List<Employee> employees = new ArrayList<>();
//        try {
//            Statement st = MyCon.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * FROM Employee;");
//            while (rs.next()) {
//                employees.add(new Employee(
//                        rs.getInt("IdEmp"),
//                        rs.getString("NomEmp"),
//                        rs.getFloat("Salaire"),
//                        rs.getInt("Age"),
//                        rs.getInt("RefDept")
//                ));
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return employees;
//    }
//
//    @Override
//    public Optional<Employee> Read(Integer Id) {
//        String sql = "SELECT * FROM Employee WHERE IdEmp = ?";
//        ResultSet rs;
//        Employee employee = null;
//        try {
//            PreparedStatement pst = MyCon.prepareStatement(sql);
//            pst.setInt(1, Id);
//            rs = pst.executeQuery();
//            while (rs.next()) {
//                System.out.println("Employee found ");
//                employee = new Employee(
//                        rs.getInt("IdEmp"),
//                        rs.getString("NomEmp"),
//                        rs.getFloat("Salaire"),
//                        rs.getInt("Age"),
//                        rs.getInt("RefDept")
//                );
//                return Optional.of(employee);
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean Update(Employee emp, Integer Id) {
//        String sql = "UPDATE Employee SET NomEmp = ?, Salaire = ?, Age = ?, RefDept = ? WHERE IdEmp = ?";
//
//        try {
//            PreparedStatement pst = MyCon.prepareStatement(sql);
//            pst.setString(1, emp.getNomEmp());
//            pst.setFloat(2, emp.getSalaire());
//            pst.setInt(3, emp.getAge());
//            pst.setInt(4, emp.getRefDept());
//            pst.setInt(5, Id);
//
//            pst.executeUpdate();
//
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    @Override
//    public boolean Delete(Integer Id) {
//        String sql = "DELETE FROM Employee WHERE IdEmp = ?";
//        try {
//            PreparedStatement pst = MyCon.prepareStatement(sql);
//            pst.setInt(1, Id);
//
//            int Lig = pst.executeUpdate();
//
//            if (Lig != 0)
//                System.out.println(Lig + " employee delete with success");
//            else
//                System.out.println("No employee ");
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    @Override
//    public Long Count() {
//        String sql = "SELECT COUNT(*) FROM Employee;";
//        Statement st;
//        ResultSet resultSet;
//
//        try {
//            st = MyCon.createStatement();
//            resultSet = st.executeQuery(sql);
//
//            if (resultSet.next())
//                return resultSet.getLong(1);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        return null;
//    }
//}
package com.employe.employe;

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

    public void updateEmployee(Employee employee) {
        String query = "UPDATE Employee SET NomEmp=?, Salaire=?, Age=?, RefDept=? WHERE IdEmp=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, employee.getNomEmp());
            pst.setFloat(2, Float.parseFloat(employee.getSalaire()));
            pst.setInt(3, Integer.parseInt(employee.getAge()));
            pst.setInt(4, Integer.parseInt(employee.getRefDept()));
            pst.setInt(5, Integer.parseInt(employee.getIdEmp()));
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int employeeId) {
        String query = "DELETE FROM Employee WHERE IdEmp=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, employeeId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Employee> getAllEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String query = "SELECT e.IdEmp, e.NomEmp, e.Salaire, e.Age, d.NomDept " +
                "FROM Employee e " +
                "JOIN Departement d ON e.RefDept = d.IdDept;";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();
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

    public ObservableList<Employee> getEmployeesByDepartment(String departmentName) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT e.IdEmp, e.NomEmp, e.Salaire, e.Age, d.NomDept " +
                            "FROM Employee e " +
                            "JOIN Departement d ON e.RefDept = d.IdDept " +
                            "WHERE d.NomDept = ?");
            pst.setString(1, departmentName);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();
                // Set employee properties based on ResultSet
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }
}
