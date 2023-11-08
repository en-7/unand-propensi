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
@Table(name = "tugas_akhir")
public class TugasAkhirModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tugas_akhir", updatable = false, nullable = false)
    private Long idTugasAkhir;

    @Column(name = "nilai_toefl", nullable = false)
    private Long nilaiToefl;

    @Lob
    @Column(name = "risalah_semhas", nullable = false)
    private byte[] risalahSemhas;

    @Lob
    @Column(name = "perbaikan_laporan_ta", nullable = false)
    private byte[] perbaikanLaporanTa;

    @Lob
    @Column(name = "krs_pengambilan_ta", nullable = false)
    private byte[] krsPengambilanTa;

    @Lob
    @Column(name = "surat_bebas_lab", nullable = false)
    private byte[] suratBebasLab;

    @Lob
    @Column(name = "surat_persetujuan_sidang", nullable = false)
    private byte[] suratPersetujuanSidang;

    @Lob
    @Column(name = "kartu_mengikuti_seminar", nullable = false)
    private byte[] kartuMengikutiSeminar;

    @Lob
    @Column(name = "bukti_nilai_kp", nullable = false)
    private byte[] buktiNilaiKp;

    @Lob
    @Column(name = "draft_laporan_ta", nullable = false)
    private byte[] draftLaporanTA;

    @Lob
    @Column(name = "bukti_lembar_asistensi", nullable = false)
    private byte[] buktiLembarAsistensi;

    @Lob
    @Column(name = "bukti_toefl", nullable = false)
    private byte[] buktiToefl;

    @Lob
    @Column(name = "lembar_konversi_nilai", nullable = false)
    private byte[] lembarKonversiNilai;

    @Lob
    @Column(name = "transkrip_nilai_terbaru", nullable = false)
    private byte[] transkripNilaiTerbaru;

    @Size(max = 100)
    @Column(name = "status_dokumen")
    private String statusDokumen;

    @Size(max = 100)
    @Column(name = "status_ta")
    private String statusTa;

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
    @JoinColumn(name = "id_seminar_proposal")
    private SeminarProposalModel seminarProposal;

    @OneToOne
    @JoinColumn(name = "id_seminar_hasil")
    private SeminarHasilModel seminarHasil;

    @OneToOne(mappedBy = "tugasAkhir")
    private JadwalSidangModel jadwalSidang;
    
}