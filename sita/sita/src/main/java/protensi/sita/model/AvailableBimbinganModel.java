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
@Table(name = "available_bimbingan")
public class AvailableBimbinganModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_available_bimbingan", updatable = false, nullable = false)
    private Long idAvailableBimbingan;

    @ElementCollection
    @CollectionTable(name = "available_bimbingan_times", joinColumns = @JoinColumn(name = "id_available_bimbingan"))
    @Column(name = "available_time", nullable = false)
    private Set<LocalDateTime> available;

    @OneToOne
    @JoinColumn(name = "id_pembimbing", nullable = false)
    private PembimbingModel pembimbing;
}
