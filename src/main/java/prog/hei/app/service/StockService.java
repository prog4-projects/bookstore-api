package prog.hei.app.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog.hei.app.dto.stock.response.LowStockEditionResponse;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;
import prog.hei.app.repository.StockMovementRepository;

@Service
@AllArgsConstructor
public class StockService {

  private final StockMovementRepository stockMovementRepository;

  @Transactional(readOnly = true)
  public List<LowStockEditionResponse> getLowStockEditions(int threshold) {
    List<Object[]> results = stockMovementRepository.findLowStockEditions(threshold);
    return results.stream().map(this::mapToDto).toList();
  }

  private LowStockEditionResponse mapToDto(Object[] row) {
    return new LowStockEditionResponse(
        (UUID) row[0],
        (UUID) row[1],
        (String) row[2],
        (String) row[3],
        row[4] != null ? BookLanguageEnum.valueOf((String) row[4]) : null,
        row[5] != null ? BookFormatEnum.valueOf((String) row[5]) : null,
        ((Number) row[6]).intValue(),
        (String) row[7],
        (BigDecimal) row[8],
        row[9] != null ? ((Timestamp) row[9]).toLocalDateTime() : null,
        ((Number) row[10]).intValue());
  }
}
