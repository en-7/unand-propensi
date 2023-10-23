package protensi.sita.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "admin")
@Getter
@Setter
public class AdminModel extends UserModel {

}
