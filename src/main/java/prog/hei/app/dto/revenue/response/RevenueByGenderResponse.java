package prog.hei.app.dto.revenue.response;

import java.math.BigDecimal;
import prog.hei.app.entity.enums.BookGenderEnum;

public record RevenueByGenderResponse(BookGenderEnum gender, BigDecimal revenue) {}
