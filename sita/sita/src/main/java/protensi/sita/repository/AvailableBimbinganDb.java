package protensi.sita.repository;

import protensi.sita.model.AvailableBimbinganModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailableBimbinganDb extends JpaRepository<AvailableBimbinganModel, Long> {
    Optional<AvailableBimbinganModel> findByIdAvailableBimbingan(Long idAvailableBimbingan);
    
}
