module com.clochelabs.companyview {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires javafx.web;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.clochelabs.view to javafx.fxml;
    exports com.clochelabs.view;
    exports com.clochelabs;
}