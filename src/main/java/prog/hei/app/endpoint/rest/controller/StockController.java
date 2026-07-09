package prog.hei.app.endpoint.rest.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prog.hei.app.dto.stock.response.LowStockEditionResponse;
import prog.hei.app.service.StockService;

@RestController
@AllArgsConstructor
@RequestMapping("/stocks")
public class StockController {

  private final StockService stockService;

  @GetMapping("/low-stock")
  public List<LowStockEditionResponse> getLowStock(
      @RequestParam(name = "threshold", defaultValue = "3") int threshold) {
    return stockService.getLowStockEditions(threshold);
  }
}
