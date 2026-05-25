package com.abhinav.College.Discovery.Platform.Repository;


import com.abhinav.College.Discovery.Platform.Models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a " +
            "JOIN FETCH a.user u " +
            "WHERE a.question.id = :questionId " +
            "ORDER BY a.createdAt ASC")
    List<Answer> findAnswersByQuestionIdWithUser(@Param("questionId") Long questionId);
}
