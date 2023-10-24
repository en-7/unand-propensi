package protensi.sita.service;

import protensi.sita.model.JadwalSidangModel;

import java.util.List;

public interface jadwalSidangSeminarService {

    //Method get List Mahasiswa mendaftar Sidang/Seminar;
    List<JadwalSidangModel> getListJadwalSidang();

    //Method getUserbyId
    JadwalSidangModel getJadwalSidangById(long id);


    //set Jadwal Sempro
    void setJadwalSidang(JadwalSidangModel sempro);

    //delete penjadwalan Sempro
    void deletesJadwalSidangSeminar(Long id);

}



