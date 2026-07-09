package prog.hei.app.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prog.hei.app.entity.SaleItem;

public interface SaleItemRepository extends JpaRepository<SaleItem, UUID> {

  @Query(
      """
      SELECT b.gender, COALESCE(SUM(si.quantity * si.unitPrice), 0)
      FROM SaleItem si
      JOIN si.bookEdition be
      JOIN be.book b
      JOIN si.sale s
      WHERE s.status = 'COMPLETED'
      GROUP BY b.gender""")
  List<Object[]> findRevenueByGender();
}
