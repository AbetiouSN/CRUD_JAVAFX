module com.employe.employe {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jdk.jfr;
    requires lombok;
    requires java.desktop;

    opens com.employe.employe to javafx.fxml;
    exports com.employe.employe;
}