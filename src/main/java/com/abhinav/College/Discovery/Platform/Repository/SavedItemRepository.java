package com.abhinav.College.Discovery.Platform.Repository;


import com.abhinav.College.Discovery.Platform.Models.SavedItem;
import com.abhinav.College.Discovery.Platform.Models.SavedType;
import com.abhinav.College.Discovery.Platform.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavedItemRepository extends JpaRepository<SavedItem, Long> {
    List<SavedItem> findByUserAndSavedType(Users user, SavedType savedType);
}