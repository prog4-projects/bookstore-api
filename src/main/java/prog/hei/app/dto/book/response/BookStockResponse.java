package prog.hei.app.dto.book.response;

import java.util.UUID;

public record BookStockResponse(UUID id, String title, Integer stock) {}
