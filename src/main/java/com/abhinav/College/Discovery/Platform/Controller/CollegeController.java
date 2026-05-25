package com.abhinav.College.Discovery.Platform.Controller;


import com.abhinav.College.Discovery.Platform.Models.College;
import com.abhinav.College.Discovery.Platform.Repository.CollegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colleges")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CollegeController {

    @Autowired
    private final CollegeRepository collegeRepository;

    @GetMapping
    public ResponseEntity<Slice<College>> getColleges(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int limit) {

        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by("id").ascending());

        Slice<College> colleges = collegeRepository.findCollegesByFilters(search, state, minRating, lastId, pageRequest);
        return ResponseEntity.ok(colleges);
    }


    @GetMapping("/{slug}")
    public ResponseEntity<College> getCollegeBySlug(@PathVariable String slug) {
        return collegeRepository.findBySlug(slug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/compare")
    public ResponseEntity<?> compareColleges(@RequestParam List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body("Please provide college IDs to compare.");
        }
        if (ids.size() > 3) {
            return ResponseEntity.badRequest().body("You can only compare a maximum of 3 colleges side-by-side.");
        }

        List<College> comparisonMatrix = collegeRepository.findCollegesForComparison(ids);
        return ResponseEntity.ok(comparisonMatrix);
    }
}
