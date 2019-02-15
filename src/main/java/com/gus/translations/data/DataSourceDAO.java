package com.gus.translations.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSourceDAO {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private String url = "jdbc:mysql://localhost:3306/translations?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String user = "root";
    private String password = "root";

    public void readDataBase() throws Exception {
        try {
            connect = DriverManager.getConnection(url, user, password);
            statement = connect.createStatement();
            resultSet = statement
                    .executeQuery("select * from dictionary");
        } catch (Exception e) {
            throw e;
        }
    }

    public List<String> getUrlAddressList() throws SQLException {
        List<String> urlAdresses = new ArrayList<>();
        while (resultSet.next()) {
            String link = resultSet.getString("url");
            urlAdresses.add(link);
        }
        return urlAdresses;
    }

    public void updateEnglishTerm(String englishTerm, String englishDefinition, String englishExplanation) throws SQLException {
        connect = DriverManager.getConnection(url, user, password);
        String query = "update translations.dictionary set english_definition = ?, english_methodological_explanation =? where english_term = ?";
        preparedStatement = connect.prepareStatement(query);
        preparedStatement.setString(1, englishDefinition);
        preparedStatement.setString(2, englishExplanation);
        preparedStatement.setString(3, englishTerm);
        preparedStatement.executeUpdate();
//        connect.close();

    }


    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}
