package prog.hei.app.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog.hei.app.dto.revenue.response.RevenueByGenderResponse;
import prog.hei.app.entity.enums.BookGenderEnum;
import prog.hei.app.repository.SaleItemRepository;

@Service
@AllArgsConstructor
public class RevenueService {

  private final SaleItemRepository saleItemRepository;

  @Transactional(readOnly = true)
  public List<RevenueByGenderResponse> getRevenueByGender() {
    List<Object[]> results = saleItemRepository.findRevenueByGender();

    Map<BookGenderEnum, BigDecimal> revenueMap = new HashMap<>();
    for (Object[] row : results) {
      BookGenderEnum gender = (BookGenderEnum) row[0];
      BigDecimal revenue = (BigDecimal) row[1];
      revenueMap.put(gender, revenue != null ? revenue : BigDecimal.ZERO);
    }

    return Arrays.stream(BookGenderEnum.values())
        .map(g -> new RevenueByGenderResponse(g, revenueMap.getOrDefault(g, BigDecimal.ZERO)))
        .sorted(Comparator.comparing(r -> r.gender().name()))
        .toList();
  }
}
