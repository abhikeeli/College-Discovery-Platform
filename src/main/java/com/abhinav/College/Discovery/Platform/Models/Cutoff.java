package com.abhinav.College.Discovery.Platform.Models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cutoffs", indexes = {
        @Index(name = "idx_cutoffs_lookup", columnList = "exam, category, closing_rank")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Cutoff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String exam; // e.g., "JEE_MAIN", "CAT"

    private String quota = "All India";
    private String category = "General";

    @Column(name = "opening_rank", nullable = false)
    private Integer openingRank;

    @Column(name = "closing_rank", nullable = false)
    private Integer closingRank;

    private Integer year;
}