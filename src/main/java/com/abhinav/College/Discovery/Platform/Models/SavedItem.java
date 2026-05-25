package com.abhinav.College.Discovery.Platform.Models;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "saved_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "saved_type", "target_reference"})
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SavedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Enumerated(EnumType.STRING)
    @Column(name = "saved_type", nullable = false)
    private SavedType savedType;


    @Column(name = "target_reference", nullable = false)
    private String targetReference;
}
