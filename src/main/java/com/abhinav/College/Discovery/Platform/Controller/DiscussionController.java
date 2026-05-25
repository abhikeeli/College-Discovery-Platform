package com.abhinav.College.Discovery.Platform.Controller;

import com.abhinav.College.Discovery.Platform.Models.*;
import com.abhinav.College.Discovery.Platform.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discussions")
@RequiredArgsConstructor
public class DiscussionController {

    @Autowired
    private final QuestionRepository questionRepository;
    @Autowired
    private final AnswerRepository answerRepository;
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final CollegeRepository collegeRepository;


    @GetMapping
    public ResponseEntity<Slice<Question>> browseDiscussions(
            @RequestParam(required = false) Long collegeId,
            @RequestParam(defaultValue = "0") int page) {

        PageRequest pageRequest = PageRequest.of(page, 15);

        if (collegeId != null) {
            return ResponseEntity.ok(questionRepository.findByCollegeIdOrderByCreatedAtDesc(collegeId, pageRequest));
        }

        return ResponseEntity.ok(questionRepository.findByOrderByCreatedAtDesc(pageRequest));
    }

    @PostMapping("/ask")
    public ResponseEntity<Question> askQuestion(@RequestBody Question question,@RequestBody(required = false) Long collegeId,@AuthenticationPrincipal UserDetails userDetails) {
        Users currentUser = userRepo.findByusername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Context Missing"));
        question.setUser(currentUser);
        if(collegeId != null){
            College targetCollege = collegeRepository.findById(collegeId).orElseThrow(()->new IllegalArgumentException("Target College not found"));
            question.setCollege(targetCollege);
        }
        return ResponseEntity.ok(questionRepository.save(question));
    }


    @PostMapping("/{questionId}/answer")
    public ResponseEntity<Answer> answerQuestion(@PathVariable Long questionId, @RequestBody Answer answer, @AuthenticationPrincipal UserDetails userDetails) {
        Users currentUser = userRepo.findByusername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Context Missing"));
        Question targetQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question element not found"));

        answer.setUser(currentUser);
        answer.setQuestion(targetQuestion);
        return ResponseEntity.ok(answerRepository.save(answer));
    }
}
