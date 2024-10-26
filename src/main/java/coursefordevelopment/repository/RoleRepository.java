package coursefordevelopment.repository;

import com.example.coursefordevelopment.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsByRoleName(String username);
    Optional<Role> findByRoleName(String username);
}
