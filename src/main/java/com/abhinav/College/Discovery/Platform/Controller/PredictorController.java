package com.abhinav.College.Discovery.Platform.Controller;


import com.abhinav.College.Discovery.Platform.DTO.PredictorRequest;
import com.abhinav.College.Discovery.Platform.Models.Cutoff;
import com.abhinav.College.Discovery.Platform.Repository.CutoffRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/predictor")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PredictorController {
    @Autowired
    private final CutoffRepository cutoffRepository;

    @PostMapping
    public ResponseEntity<List<Cutoff>> predictColleges(@Valid @RequestBody PredictorRequest request) {
        List<Cutoff> recommendations = cutoffRepository.findMatchingColleges(
                request.getExam(),
                request.getCategory(),
                request.getRank()
        );
        return ResponseEntity.ok(recommendations);
    }
}
