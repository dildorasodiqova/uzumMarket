package uz.pdp.uzummarket.service.orderService;

import uz.pdp.uzummarket.Dto.requestSTO.OrderCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO add(OrderCreateDTO dto);
}
