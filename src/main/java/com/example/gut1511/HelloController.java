package com.example.gut1511;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloController {
    public Button login_to_account_button;
    public TextField field_login;
    public PasswordField field_password;
    public Label false_auth;
    DB db = null;

    public void initialize() {
        db = new DB();
        login_to_account();
    }

    public void login_to_account() {
        login_to_account_button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() // При нажатии на кнопку добавляем задания в comboBox
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                try {
                    String selectedUser = field_login.getText(); // Получаем покупателя
                    String selectedPassword = field_password.getText(); // Получаем пароль

                    if (db.getUserId(selectedUser, selectedPassword) != -1) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Marks.fxml"));

                        Stage stage=new Stage();
                        Scene scene = new Scene(fxmlLoader.load());
                        stage.setTitle("Оценки");
                        stage.setScene(scene);

                        Stage loginStage = (Stage) login_to_account_button.getScene().getWindow();
                        // Закрытие окна авторизации
                        loginStage.close();

                        stage.show();

                    } else {
                        field_login.setText("");
                        field_password.setText("");
                        false_auth.setText("Неправильный логин или пароль");
                    }

                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}