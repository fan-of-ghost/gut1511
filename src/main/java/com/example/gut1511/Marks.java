package com.example.gut1511;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Marks {

    @FXML
    public ComboBox<String> students;

    @FXML
    public ListView<String> listDb;

    private DB db;

    public void initialize() {
        db = new DB();
        insertStudent();

        // Добавляем слушателя событий к ComboBox
        students.setOnAction(event -> showMarks());

        // Настройка отображения ячеек в ListView
        listDb.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }

    private void insertStudent() {
        try {
            Connection connection = db.getDbConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT fio FROM Student");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String data = resultSet.getString("fio");
                students.getItems().add(data);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMarks() {
        String selectedStudent = students.getValue();

        try {
            Connection connection = db.getDbConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT Student.fio, Subject.name, mark FROM Marks JOIN Student JOIN Subject ON Marks.id_student=Student.id_student AND Marks.id_subj=Subject.id_subj WHERE fio = ?");
            statement.setString(1, selectedStudent);
            ResultSet resultSet = statement.executeQuery();

            listDb.getItems().clear(); // Очищаем текущие данные в ListView

            while (resultSet.next()) {
                String fio = resultSet.getString("fio");
                String subjectName = resultSet.getString("name");
                String mark = resultSet.getString("mark");

                // Сконструируем строку, содержащую данные из нескольких столбцов
                String rowData = fio + " - " + subjectName + " - " + mark;

                listDb.getItems().add(rowData);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
