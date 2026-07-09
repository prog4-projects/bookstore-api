package prog.hei.app.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prog.hei.app.entity.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {

  @Query(
      value =
          """
          SELECT
            be.id AS edition_id,
            b.id AS book_id,
            b.title AS book_title,
            be.isbn,
            be.language,
            be.format,
            be.page_count AS page_count,
            be.publisher,
            be.price,
            be.publication_date AS publication_date,
            COALESCE(SUM(
              CASE WHEN sm.type = 'IN' THEN sm.quantity ELSE -sm.quantity END
            ), 0) AS stock
          FROM book_edition be
          JOIN book b ON b.id = be.book_id
          LEFT JOIN stock_movement sm ON sm.book_edition_id = be.id
          GROUP BY be.id, b.id, be.isbn, be.language, be.format,
                   be.page_count, be.publisher, be.price,
                   be.publication_date, b.title
          HAVING COALESCE(SUM(
            CASE WHEN sm.type = 'IN' THEN sm.quantity ELSE -sm.quantity END
          ), 0) <= :threshold
          ORDER BY stock ASC
          """,
      nativeQuery = true)
  List<Object[]> findLowStockEditions(@Param("threshold") int threshold);
}
