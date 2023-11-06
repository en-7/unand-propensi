package protensi.sita.repository;

import protensi.sita.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDb extends JpaRepository<AdminModel, String> {

}
