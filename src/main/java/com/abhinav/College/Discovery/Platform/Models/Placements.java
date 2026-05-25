package com.abhinav.College.Discovery.Platform.Models;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "placements")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Placements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer passingYear;

    @Column(precision = 12, scale = 2)
    private BigDecimal highestPackage;

    @Column(precision = 12, scale = 2)
    private BigDecimal averagePackage;

    private Double placementPercentage;

    @ElementCollection
    @CollectionTable(name = "placement_recruiters", joinColumns = @JoinColumn(name = "placement_id"))
    @Column(name = "recruiter_name")
    private List<String> topRecruiters;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    @JsonIgnore
    private College college;
}
