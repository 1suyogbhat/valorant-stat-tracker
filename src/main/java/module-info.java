module ca.ucalgary.sbhat.projectvaloranttrackergui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;


    opens ca.ucalgary.sbhat.projectvaloranttrackergui to javafx.fxml;
    exports ca.ucalgary.sbhat.projectvaloranttrackergui;
}