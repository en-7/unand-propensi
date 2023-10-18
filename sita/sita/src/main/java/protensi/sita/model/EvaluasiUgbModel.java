package protensi.sita.model;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "evaluasi_ugb")
public class EvaluasiUgbModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluasi_ugb", updatable = false, nullable = false)
    private Long idEvaluasiUgb;

    @NotNull
    @Size(max = 100)
    @Column(name = "judul", nullable = false)
    private String judul;

    @Lob
    @Column(name = "latar_belakang", nullable = false)
    private String latarBelakang;

    @Lob
    @Column(name = "tujuan_manfaat", nullable = false)
    private String tujuanManfaat;

    @Lob
    @Column(name = "ruang_lingkup", nullable = false)
    private String ruangLingkup;

    @Lob
    @Column(name = "keterbaruan", nullable = false)
    private String keterbaruan;

    @Lob
    @Column(name = "metodologi", nullable = false)
    private String metodologi;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @OneToOne
    @JoinColumn(name = "id_ugb")
    private UgbModel ugb;

}