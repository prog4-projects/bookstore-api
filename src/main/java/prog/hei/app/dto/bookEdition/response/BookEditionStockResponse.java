package prog.hei.app.dto.bookEdition.response;

import java.util.UUID;

public record BookEditionStockResponse(UUID bookEditionId, String title, Integer stock) {}
