//package com.employe.employe;
//
//import java.util.List;
//import java.util.Optional;
//
//
//
//public class TestArticle {
//    public static void main(String[] args) {
//        DaoEmployee daoArticle = new DaoEmployee();
//        Employee article = Employee.builder()
//                .idEmp(14)
//                .nomEmp("Sofian ABETIOU")
//                .salaire(10000F)
//                .age(26)
//                .refDept(1)
//                .build();
//       daoArticle.Create(article);
//        List<Employee> articles = daoArticle.all();
//        articles.forEach(art -> System.out.println(art));
//        //System.out.println(daoArticle.all());
//
////       Optional<Article> article1 =daoArticle.Read("ART1");
////
////       article1.ifPresentOrElse(article2 -> System.out.println(
////               "Found "+ article2),
////               ()-> System.out.println("not found ")
////       );
////        Employee article = Employee.builder()
////                .designation("ARTICLE 111111")
////                .prix(177777)
////                .build();
////        daoArticle.Update(article,1);
////        System.out.println(daoArticle.all());
//    }
//}
