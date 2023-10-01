package com.gwizd.model;

import com.gwizd.enumeration.ReportType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Report {
    @SequenceGenerator(
            name = "report_sequence",
            sequenceName = "report_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "report_sequence"
    )
    private Long id;
    private String comment;
    @Lob
    private byte[] img;
    @ManyToOne(
            cascade=CascadeType.ALL
    )
    @JoinColumn(
            name = "location_coordinates_id",
            nullable = false
    )
    private LocationCoordinates locationCoordinates;
    @NotBlank
    private String locationName;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ReportType reportType;
    public Report(
            String comment,
            byte[] img,
            LocationCoordinates locationCoordinates,
            String locationName,
            ReportType reportType
    ) {
        this.comment = comment;
        this.img = img;
        this.locationCoordinates = locationCoordinates;
        this.locationName = locationName;
        this.reportType = reportType;
    }
}
