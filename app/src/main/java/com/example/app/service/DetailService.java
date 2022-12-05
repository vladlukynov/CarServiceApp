package com.example.app.service;

import com.example.app.entity.Detail;
import com.example.app.exception.NoDetailByIdException;
import com.example.app.repository.DetailRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DetailService {
    private final DetailRepository detailRepository = new DetailRepository();

    public List<Detail> getDetails() throws SQLException {
        return detailRepository.getDetails();
    }

    public Detail getDetail(int detailId) throws SQLException, NoDetailByIdException {
        List<Detail> details = getDetails();

        Optional<Detail> optionalDetail = details.stream().filter(detail -> detail.getDetailId() == detailId).findFirst();
        if (optionalDetail.isPresent()) {
            return optionalDetail.get();
        }

        throw new NoDetailByIdException("No detail by id " + detailId);
    }

    public void addDetail(Detail detail) throws SQLException {
        detailRepository.addDetail(detail);
    }

    public void updateDetail(int detailId, Detail newDetail) throws SQLException {
        detailRepository.updateDetail(detailId, newDetail);
    }
}
