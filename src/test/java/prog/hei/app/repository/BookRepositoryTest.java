package prog.hei.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BookRepositoryTest {

  @Autowired private BookRepository bookRepository;
  @Autowired private EntityManager entityManager;

  private void insertBook(UUID bookId, String title) {
    entityManager
        .createNativeQuery(
            """
            INSERT INTO book
            (id, title, created_at, gender)
            VALUES (?, ?, ?, ?)
            """)
        .setParameter(1, bookId)
        .setParameter(2, title)
        .setParameter(3, LocalDateTime.now())
        .setParameter(4, "FANTASY")
        .executeUpdate();
  }

  private void insertEdition(UUID editionId, UUID bookId) {
    entityManager
        .createNativeQuery(
            """
            INSERT INTO book_edition
            (
                id,
                book_id,
                isbn,
                page_count,
                price,
                publication_date,
                language
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """)
        .setParameter(1, editionId)
        .setParameter(2, bookId)
        .setParameter(3, UUID.randomUUID().toString())
        .setParameter(4, 200)
        .setParameter(5, BigDecimal.valueOf(10000))
        .setParameter(6, LocalDateTime.now())
        .setParameter(7, "FRENCH")
        .executeUpdate();
  }

  private void insertStockMovement(UUID editionId, String type, int quantity) {

    entityManager
        .createNativeQuery(
            """
            INSERT INTO stock_movement
            (
                id,
                book_edition_id,
                type,
                quantity,
                date,
                reason
            )
            VALUES (?, ?, ?, ?, ?, ?)
            """)
        .setParameter(1, UUID.randomUUID())
        .setParameter(2, editionId)
        .setParameter(3, type)
        .setParameter(4, quantity)
        .setParameter(5, LocalDateTime.now())
        .setParameter(6, "ARRIVAL")
        .executeUpdate();
  }

  @Test
  void getStockById_shouldReturnZero_whenBookDoesNotExist() {

    Integer stock = bookRepository.getStockById(UUID.randomUUID());

    assertThat(stock).isZero();
  }

  @Test
  void getStockById_shouldReturnZero_whenBookHasNoEdition() {

    UUID bookId = UUID.randomUUID();

    insertBook(bookId, "No Edition");

    Integer stock = bookRepository.getStockById(bookId);

    assertThat(stock).isZero();
  }

  @Test
  void getStockById_shouldReturnZero_whenEditionHasNoMovement() {

    UUID bookId = UUID.randomUUID();
    UUID editionId = UUID.randomUUID();

    insertBook(bookId, "No Movement");
    insertEdition(editionId, bookId);

    Integer stock = bookRepository.getStockById(bookId);

    assertThat(stock).isZero();
  }

  @Test
  void getStockById_shouldSumAllInAndOutMovementsForSingleEdition() {

    UUID bookId = UUID.randomUUID();
    UUID editionId = UUID.randomUUID();

    insertBook(bookId, "Book IN OUT");
    insertEdition(editionId, bookId);

    insertStockMovement(editionId, "IN", 10);
    insertStockMovement(editionId, "OUT", 5);
    insertStockMovement(editionId, "OUT", 3);

    Integer stock = bookRepository.getStockById(bookId);

    assertThat(stock).isEqualTo(2);
  }

  @Test
  void getStockById_shouldAggregateOverMultipleEditions() {

    UUID bookId = UUID.randomUUID();

    UUID editionId1 = UUID.randomUUID();
    UUID editionId2 = UUID.randomUUID();

    insertBook(bookId, "Multiple Editions");

    insertEdition(editionId1, bookId);
    insertEdition(editionId2, bookId);

    insertStockMovement(editionId1, "IN", 10);
    insertStockMovement(editionId1, "OUT", 3);

    insertStockMovement(editionId2, "IN", 4);
    insertStockMovement(editionId2, "OUT", 1);

    Integer stock = bookRepository.getStockById(bookId);

    assertThat(stock).isEqualTo(10);
  }

  @Test
  void getStockById_shouldReturnNegative_whenOutExceedsIn() {

    UUID bookId = UUID.randomUUID();
    UUID editionId = UUID.randomUUID();

    insertBook(bookId, "Negative Stock");
    insertEdition(editionId, bookId);

    insertStockMovement(editionId, "IN", 2);
    insertStockMovement(editionId, "OUT", 5);

    Integer stock = bookRepository.getStockById(bookId);

    assertThat(stock).isEqualTo(-3);
  }
}
