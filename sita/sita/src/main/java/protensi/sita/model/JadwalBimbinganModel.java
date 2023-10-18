package protensi.sita.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jadwal_bimbingan")
public class JadwalBimbinganModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jadwal_bimbingan", updatable = false, nullable = false)
    private Long idJadwalBimbingan;

    @ElementCollection
    @CollectionTable(name = "jadwal_bimbingan_times", joinColumns = @JoinColumn(name = "id_jadwal_bimbingan"))
    @Column(name = "tanggal_bimbingan")
    private Set<LocalDateTime> tanggalBimbingan;

    @OneToOne
    @JoinTable(name = "mahasiswa_bimbingan")
    private MahasiswaModel mahasiswa;

    @OneToOne
    @JoinColumn(name = "id_available_bimbingan", nullable = false)
    private AvailableBimbinganModel availableBimbingan;

}
