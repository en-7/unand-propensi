package protensi.sita.model;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seminar_proposal")
public class SeminarProposalModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seminar_proposal", updatable = false, nullable = false)
    private Long idSeminarProposal;

    @Lob
    @Column(name = "draft_proposal_ta", nullable = false)
    private byte[] draftProposalTa;

    @Lob
    @Column(name = "bukti_krs", nullable = false)
    private byte[] buktiKrs;

    @Lob
    @Column(name = "persetujuan_pembimbing", nullable = false)
    private byte[] persetujuanPembimbing;

    @Size(max = 100)
    @Column(name = "status_dokumen")
    private String statusDokumen;

    @Size(max = 100)
    @Column(name = "status_seminar_proposal")
    private String statusSeminarProposal;

    @Lob
    @Column(name = "catatan")
    private String catatan;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "nilai")
    private Long nilai;
    
    @Column(name = "tanggal_lulus")
    private LocalDateTime tanggalLulus;

    @OneToOne
    @JoinColumn(name = "id_ugb")
    private UgbModel ugb;

    @OneToOne
    @JoinColumn(name = "id_jadwal_sidang")
    private JadwalSidangModel jadwalSidang;

}