package com.abhinav.College.Discovery.Platform.Controller;


import com.abhinav.College.Discovery.Platform.Models.*;
import com.abhinav.College.Discovery.Platform.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/saved")
@RequiredArgsConstructor
public class SavedItemController {

    @Autowired
    private final SavedItemRepository savedItemRepository;
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private CollegeRepository collegeRepository;


    @PostMapping
    public ResponseEntity<SavedItem> saveItem(@RequestParam SavedType type, @RequestParam String reference, @AuthenticationPrincipal UserDetails userDetails) {
        Users currentUser = userRepo.findByusername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Context Missing"));

        SavedItem item = SavedItem.builder()
                .user(currentUser)
                .savedType(type)
                .targetReference(reference)
                .build();

        return ResponseEntity.ok(savedItemRepository.save(item));
    }


    @GetMapping
    public ResponseEntity<List<SavedItem>> getMySavedItems(@RequestParam SavedType type, @AuthenticationPrincipal UserDetails userDetails) {
        Users currentUser = userRepo.findByusername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Context Missing"));
        return ResponseEntity.ok(savedItemRepository.findByUserAndSavedType(currentUser, type));
    }

    @GetMapping("/colleges")
    public ResponseEntity<List<College>> getMySavedColleges(@AuthenticationPrincipal UserDetails userDetails) {
        Users currentUser = userRepo.findByusername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Context Missing"));


        List<SavedItem> savedColleges = savedItemRepository.findByUserAndSavedType(currentUser, SavedType.COLLEGE);


        List<Long> collegeIds = savedColleges.stream()
                .map(item -> Long.parseLong(item.getTargetReference()))
                .collect(Collectors.toList());

        if (collegeIds.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }


        List<College> fullCollegeDetails = collegeRepository.findCollegesForComparison(collegeIds);
        return ResponseEntity.ok(fullCollegeDetails);
    }
    @GetMapping("/comparisons")
    public ResponseEntity<List<List<College>>> getAllMySavedComparisons(Authentication authentication) {

        Users currentUser = userRepo.findByusername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Context Missing"));


        List<SavedItem> savedComparisons = savedItemRepository.findByUserAndSavedType(currentUser, SavedType.COMPARISON);


        List<List<College>> allHydratedComparisons = savedComparisons.stream().map(item -> {


            List<Long> collegeIds = Arrays.stream(item.getTargetReference().split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());


            return collegeRepository.findCollegesForComparison(collegeIds);

        }).collect(Collectors.toList());


        return ResponseEntity.ok(allHydratedComparisons);
    }
    @GetMapping("/comparison/{savedItemId}")
    public ResponseEntity<?> recreateSavedComparison(
            @PathVariable Long savedItemId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Users currentUser = userRepo.findByusername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Context Missing"));

        SavedItem savedItem = savedItemRepository.findById(savedItemId)
                .orElseThrow(() -> new IllegalArgumentException("Saved comparison link not found"));


        if (!savedItem.getUser().getId().equals(currentUser.getId()) || savedItem.getSavedType() != SavedType.COMPARISON) {
            return ResponseEntity.status(403).body("Unauthorized to access this comparison footprint.");
        }


        List<Long> collegeIds = Arrays.stream(savedItem.getTargetReference().split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());


        List<College> hydratedComparisonMatrix = collegeRepository.findCollegesForComparison(collegeIds);
        return ResponseEntity.ok(hydratedComparisonMatrix);
    }
}
