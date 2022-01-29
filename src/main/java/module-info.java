module com.morley.myvideoplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.base;
    opens com.morley.myvideoplayer to javafx.fxml;
    exports com.morley.myvideoplayer;
}