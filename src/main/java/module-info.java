module org.example.akarigamejavafx {
  requires javafx.controls;
  requires javafx.fxml;


  opens org.example.akarigamejavafx to javafx.fxml;
  exports org.example.akarigamejavafx;
  exports org.example.akarigamejavafx.view;
}