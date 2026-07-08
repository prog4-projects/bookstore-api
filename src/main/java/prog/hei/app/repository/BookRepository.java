package prog.hei.app.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prog.hei.app.entity.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {
  @Query(
      value =
          """
          SELECT COALESCE(
              SUM(
                  CASE
                      WHEN sm.type = 'IN' THEN sm.quantity
                      ELSE -sm.quantity
                  END
              ),
              0
          )
          FROM book_edition be
          JOIN stock_movement sm
              ON sm.book_edition_id = be.id
          WHERE be.book_id = :bookId
          """,
      nativeQuery = true)
  Integer getStockById(@Param("bookId") UUID bookId);
}
