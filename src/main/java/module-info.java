module org.iljaknk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.iljaknk to javafx.fxml;
    exports org.iljaknk;
}