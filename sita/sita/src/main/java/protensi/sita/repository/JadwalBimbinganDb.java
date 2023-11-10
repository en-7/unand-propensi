package protensi.sita.repository;

import protensi.sita.model.JadwalBimbinganModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JadwalBimbinganDb extends JpaRepository<JadwalBimbinganModel, Long> {
    Optional<JadwalBimbinganModel> findByIdJadwalBimbingan(Long idJadwalBimbingan);
    Optional<JadwalBimbinganModel> findByAvailableBimbingan_IdAvailableBimbingan(Long idAvailableBimbingan);
    
}