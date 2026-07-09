package prog.hei.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import prog.hei.app.dto.revenue.response.RevenueByGenderResponse;
import prog.hei.app.entity.enums.BookGenderEnum;
import prog.hei.app.repository.SaleItemRepository;

@ExtendWith(MockitoExtension.class)
class RevenueServiceTest {

  @Mock private SaleItemRepository saleItemRepository;

  @InjectMocks private RevenueService revenueService;

  @Test
  void getRevenueByGender_shouldReturnAllGenders_whenSomeHaveRevenue() {
    Object[] fantasyRow = new Object[] {BookGenderEnum.FANTASY, BigDecimal.valueOf(150.00)};
    Object[] thrillerRow = new Object[] {BookGenderEnum.THRILLER, BigDecimal.valueOf(75.50)};

    when(saleItemRepository.findRevenueByGender()).thenReturn(List.of(fantasyRow, thrillerRow));

    List<RevenueByGenderResponse> result = revenueService.getRevenueByGender();

    assertThat(result).isNotEmpty();
    assertThat(result)
        .anyMatch(
            r ->
                r.gender() == BookGenderEnum.FANTASY
                    && r.revenue().compareTo(BigDecimal.valueOf(150.00)) == 0);
    assertThat(result)
        .anyMatch(
            r ->
                r.gender() == BookGenderEnum.THRILLER
                    && r.revenue().compareTo(BigDecimal.valueOf(75.50)) == 0);
    assertThat(result)
        .anyMatch(
            r ->
                r.gender() == BookGenderEnum.SCIENCE_FICTION
                    && r.revenue().compareTo(BigDecimal.ZERO) == 0);
    assertThat(result)
        .isSortedAccordingTo((a, b) -> a.gender().name().compareTo(b.gender().name()));
    verify(saleItemRepository).findRevenueByGender();
  }

  @Test
  void getRevenueByGender_shouldReturnAllGendersWithZero_whenNoRevenues() {
    when(saleItemRepository.findRevenueByGender()).thenReturn(List.of());

    List<RevenueByGenderResponse> result = revenueService.getRevenueByGender();

    assertThat(result).hasSize(BookGenderEnum.values().length);
    assertThat(result).allMatch(r -> r.revenue().compareTo(BigDecimal.ZERO) == 0);
    assertThat(result)
        .isSortedAccordingTo((a, b) -> a.gender().name().compareTo(b.gender().name()));
    verify(saleItemRepository).findRevenueByGender();
  }

  @Test
  void getRevenueByGender_shouldHandleNullRevenueInRow() {
    Object[] nullRevenueRow = new Object[] {BookGenderEnum.FANTASY, null};

    when(saleItemRepository.findRevenueByGender()).thenReturn(List.<Object[]>of(nullRevenueRow));

    List<RevenueByGenderResponse> result = revenueService.getRevenueByGender();

    assertThat(result)
        .anyMatch(
            r ->
                r.gender() == BookGenderEnum.FANTASY
                    && r.revenue().compareTo(BigDecimal.ZERO) == 0);
  }
}
