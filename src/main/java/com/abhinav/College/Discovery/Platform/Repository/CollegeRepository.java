package com.abhinav.College.Discovery.Platform.Repository;

import com.abhinav.College.Discovery.Platform.Models.College;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {


    Optional<College> findBySlug(String slug);


    @Query("SELECT DISTINCT c FROM College c " +
            "LEFT JOIN c.courses co " +
            "WHERE (CAST(:search AS string) IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%'))) " +
            "AND (CAST(:state AS string) IS NULL OR LOWER(c.locationState) = LOWER(CAST(:state AS string))) " +
            "AND (:minRating IS NULL OR c.rating >= :minRating) " +
            "AND (:lastId IS NULL OR c.id > :lastId)")
    Slice<College> findCollegesByFilters(
            @Param("search") String search,
            @Param("state") String state,
            @Param("minRating") Double minRating,
            @Param("lastId") Long lastId,
            Pageable pageable);

    @Query("SELECT c FROM College c " +
            "LEFT JOIN FETCH c.courses " +
            "LEFT JOIN FETCH c.placements " +
            "WHERE c.id IN :ids")
    List<College> findCollegesForComparison(@Param("ids") List<Long> ids);
}
