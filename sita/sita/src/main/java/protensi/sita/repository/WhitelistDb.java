package protensi.sita.repository;

import protensi.sita.model.WhitelistModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhitelistDb extends JpaRepository<WhitelistModel, String> {
    Optional<WhitelistModel> findByUsername(String username);
}
