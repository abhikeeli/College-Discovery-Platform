package com.abhinav.College.Discovery.Platform.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "colleges",
        indexes = {
                @Index(name = "idx_colleges_slug", columnList = "slug", unique = true),
                @Index(name = "idx_colleges_rating", columnList = "rating DESC")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "College name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Slug is mandatory")
    @Size(max = 255, message = "Slug cannot exceed 255 characters")
    @Column(nullable = false, unique = true)
    private String slug;

    @NotBlank(message = "City is mandatory")
    @Size(max = 100, message = "City name cannot exceed 100 characters")
    @Column(name = "location_city", nullable = false)
    private String locationCity;

    @NotBlank(message = "State is mandatory")
    @Size(max = 100, message = "State name cannot exceed 100 characters")
    @Column(name = "location_state", nullable = false)
    private String locationState;

    @Min(value = 1000, message = "Invalid established year")
    @Max(value = 2026, message = "Established year cannot be in the future")
    @Column(name = "established_year")
    private Integer establishedYear;

    @Min(value = 0, message = "Rating cannot be less than 0.0")
    @Max(value = 5, message = "Rating cannot be greater than 5.0")
    private Double rating;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Course> courses = new ArrayList<>();


    @OneToOne(mappedBy = "college", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Placements placements;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.slug != null) {
            this.slug = this.slug.toLowerCase().trim().replaceAll("[^a-z0-9\\-]+", "-");
        }
    }
}