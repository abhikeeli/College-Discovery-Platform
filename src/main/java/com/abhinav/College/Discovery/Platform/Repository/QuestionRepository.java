package com.abhinav.College.Discovery.Platform.Repository;


import com.abhinav.College.Discovery.Platform.Models.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT DISTINCT q FROM Question q JOIN FETCH q.user ORDER BY q.createdAt DESC")
    Slice<Question> findByOrderByCreatedAtDesc(Pageable pageable);


    List<Question> findByCollegeId(Long collegeId);
}
