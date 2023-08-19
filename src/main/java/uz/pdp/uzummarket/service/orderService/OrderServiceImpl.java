package uz.pdp.uzummarket.service.orderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.OrderCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.OrderResponseDTO;
import uz.pdp.uzummarket.entity.Order;
import uz.pdp.uzummarket.repository.OrderProductRepository;
import uz.pdp.uzummarket.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private  final OrderProductRepository orderProductRepository;
    @Override
    public OrderResponseDTO add(OrderCreateDTO dto) {
        UUID userId = dto.getUserId();
        List<Order> allByUserId = orderRepository.findAllByUserId(userId);
        double price = 0d;
        for (Order order : allByUserId) {
            price += order.getPrice();
        }
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(userId,price, )

    }
}
