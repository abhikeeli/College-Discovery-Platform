package com.abhinav.College.Discovery.Platform.Repository;

import com.abhinav.College.Discovery.Platform.Models.Cutoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CutoffRepository extends JpaRepository<Cutoff, Long> {

    @Query("SELECT cut FROM Cutoff cut " +
            "JOIN FETCH cut.college c " +
            "JOIN FETCH cut.course co " +
            "WHERE LOWER(cut.exam) = LOWER(:exam) " +
            "AND LOWER(cut.category) = LOWER(:category) " +
            "AND cut.closingRank >= :rank " +
            "ORDER BY cut.closingRank ASC")
    List<Cutoff> findMatchingColleges(@Param("exam") String exam,
                                      @Param("category") String category,
                                      @Param("rank") Integer rank);
}
