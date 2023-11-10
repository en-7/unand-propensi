package protensi.sita.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import protensi.sita.model.PembimbingModel;
import protensi.sita.repository.PembimbingDb;


@Service
@Transactional
public class PembimbingServiceImpl implements PembimbingService {
    @Autowired
    PembimbingDb pembimbingDb;

    public PembimbingModel findPembimbingById(Long idUser) {
        return pembimbingDb.findByIdUser(idUser);
    }
}