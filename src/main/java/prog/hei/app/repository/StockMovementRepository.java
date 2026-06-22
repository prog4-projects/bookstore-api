package prog.hei.app.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prog.hei.app.entity.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {

    @Query("""
    SELECT 
      COALESCE(SUM(CASE WHEN m.type = 'IN' THEN m.quantity ELSE 0 END), 0) -
      COALESCE(SUM(CASE WHEN m.type = 'OUT' THEN m.quantity ELSE 0 END), 0)
    FROM StockMovement m
    WHERE m.bookEdition.id = :bookEditionId
  """)
    Integer computeStock(UUID bookEditionId);
}