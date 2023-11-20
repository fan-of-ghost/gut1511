package com.example.gut1511;

import java.sql.*;

public class DB {
    private final String HOST = "127.0.0.1";
    private final String PORT = "3306";
    private final String DB_NAME = "15_11_gut";
    private final String LOGIN = "root";
    private final String PASS = "";

    private Connection dbConn = null;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://localhost:" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }
    public int getUserId(String login, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_student FROM Student WHERE login =? AND password =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        prSt.setString(1, login); // Установка значения для первого параметра
        prSt.setString(2, password); // Установка значения для второго параметра
        ResultSet resultSet = prSt.executeQuery();


        if (resultSet.next()) {
            return resultSet.getInt("id_student");
        } return -1; // Возвращаем -1, если ничего не найден
    }
}
