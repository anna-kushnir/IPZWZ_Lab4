package b41.lab4.repository;

import b41.lab4.data.Viewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewerRepository extends JpaRepository<Viewer, Long> {
}
