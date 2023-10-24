package protensi.sita.model;

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
public class JadwalSidangModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jadwal_sidang", updatable = false, nullable = false)
    private Long idJadwalSidang;

    @Column(name = "tanggal_sempro")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalSempro;

    @Column(name = "tanggal_semhas")
    private LocalDateTime tanggalSemhas;

    @Column(name = "tanggal_sidang_ta")
    private LocalDateTime tanggalSidangTa;

    @OneToOne
    @JoinColumn(name = "id_ugb")
    private UgbModel ugb;

    @OneToOne
    @JoinColumn(name = "id_seminar_proposal")
    private SeminarProposalModel seminarProposal;

    @OneToOne
    @JoinColumn(name = "id_seminar_hasil")
    private SeminarHasilModel seminarHasil;

    @OneToOne
    @JoinColumn(name = "id_tugas_akhir")
    private TugasAkhirModel tugasAkhir;
}