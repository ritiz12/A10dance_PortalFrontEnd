module com.example.a10dance_frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires android.json;

    opens com.example.a10dance_frontend to javafx.fxml;
    exports com.example.a10dance_frontend;
}