package protensi.sita.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "timeline")
public class TimelineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_timeline", updatable = false, nullable = false)
    private Long idTimeline;

    @Column(name = "start_date_ugb")
    private LocalDateTime startDateUgb;

    @Column(name = "end_date_ugb")
    private LocalDateTime endDateUgb;

    @Column(name = "start_date_sempro")
    private LocalDateTime startDateSempro;

    @Column(name = "end_date_sempro")
    private LocalDateTime endDateSempro;

    @Column(name = "publish_nilai_sempro")
    private LocalDateTime publishNilaiSempro;

    @Column(name = "start_date_semhas")
    private LocalDateTime startDateSemhas;

    @Column(name = "end_date_semhas")
    private LocalDateTime endDateSemhas;

    @Column(name = "publish_nilai_semhas")
    private LocalDateTime publishNilaiSemhas;

    @Column(name = "start_date_ta")
    private LocalDateTime startDateTa;

    @Column(name = "end_date_ta")
    private LocalDateTime endDateTa;

    @Column(name = "publish_nilai_ta")
    private LocalDateTime publishNilaiTa;
}