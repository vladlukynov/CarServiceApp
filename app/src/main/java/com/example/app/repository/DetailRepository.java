package com.example.app.repository;

import com.example.app.entity.Detail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.utils.DatabaseAuth.*;

public class DetailRepository {
    public List<Detail> getDetails() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM GetDetailsInfo");
            ResultSet resultSet = statement.executeQuery();

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
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC AddDetail ?,?,?");
            statement.setString(1, detail.getDetailName());
            statement.setDouble(2, detail.getPrice());
            statement.setInt(3, detail.getQuantity());

            statement.execute();
        }
    }

    public void updateDetail(int detailId, Detail newDetail) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, userName, password)) {
            PreparedStatement statement = connection.prepareStatement("EXEC EditDetail ?,?,?,?");
            statement.setInt(1, detailId);
            statement.setString(2, newDetail.getDetailName());
            statement.setDouble(3, newDetail.getPrice());
            statement.setInt(4, newDetail.getQuantity());

            statement.execute();
        }
    }
}
