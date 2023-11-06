package protensi.sita.service;

import protensi.sita.model.SeminarHasilModel;

import java.util.List;

public interface SeminarHasilService {

    void addSeminarHasil(SeminarHasilModel seminarHasil);

    List<SeminarHasilModel> findAllSeminarHasil();
}
