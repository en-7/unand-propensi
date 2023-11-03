package protensi.sita.repository;

import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.UgbModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UgbDb extends JpaRepository<UgbModel, Long> {
   
}
