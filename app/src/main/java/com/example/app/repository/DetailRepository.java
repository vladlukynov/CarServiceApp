package com.example.app.repository;

import com.example.app.entity.Detail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.utils.DatabaseAuth.*;

public class DetailRepository {
    public List<Detail> getDetails() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM GetDetailsInfo")) {
            List<Detail> details = new ArrayList<>();
            while (resultSet.next()) {
                Detail detail = new Detail(resultSet.getInt("DetailId"),
                        resultSet.getString("DetailName"),
                        resultSet.getInt("Price"),
                        resultSet.getInt("Quantity"));
                details.add(detail);
            }
            return details;
        }
    }

    public void addDetail(Detail detail) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement()) {
            statement.execute("EXEC AddDetail '" + detail.getDetailName() + "'," +
                    detail.getPrice() + "," +
                    detail.getQuantity());
        }
    }

    public void updateDetail(int detailId, Detail newDetail) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             Statement statement = connection.createStatement()) {
            statement.execute("EXEC EditDetail " + detailId + ",'" +
                    newDetail.getDetailName() + "'," +
                    newDetail.getPrice() + "," +
                    newDetail.getQuantity());
        }
    }
}
