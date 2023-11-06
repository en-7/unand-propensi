package protensi.sita.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jadwal_sidang")
//@JsonIgnoreProperties(value={"listPenyelenggara"}, allowSetters = true)
public class JadwalSidangModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jadwal_sidang", updatable = false, nullable = false)
    private Long idJadwalSidang;

    @Column(name = "tanggal_sempro")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime tanggalSempro;            

    @Column(name = "tanggal_semhas")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime tanggalSemhas;

    @Column(name = "tanggal_sidang_ta")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime tanggalSidangTa;

    @Column(name= "tempat_sempro")
    private String tempatSempro;

    @Column(name= "tempat_semhas")
    private String tempatSemhas;

    @Column(name= "tempat_sidang_ta")
    private String tempatSidangTA;

    @OneToOne
    @JoinColumn(name = "id_seminar_proposal")
    private SeminarProposalModel seminarProposal;

    @OneToOne
    @JoinColumn(name = "id_seminar_hasil")
    private SeminarHasilModel seminarHasil;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tugas_akhir")
    private TugasAkhirModel tugasAkhir;
}