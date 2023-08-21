package uz.pdp.uzummarket.service.bucketService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.Dto.responceDTO.BasketProductDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BasketResponseDTO;
import uz.pdp.uzummarket.entity.*;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.BasketProductRepository;
import uz.pdp.uzummarket.repository.BasketRepository;
import uz.pdp.uzummarket.repository.ProductRepository;
import uz.pdp.uzummarket.service.userService.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final BasketProductRepository basketProductRepository;

    @Override
    public BasketResponseDTO getById(UUID basketId) {
        Basket basket = basketRepository.getById(basketId);
        List<BasketProduct> allByBasketId = basketProductRepository.findAllByBasketId(basket.getId());
        return parse(basket, allByBasketId);
    }

    /**
     * This method for Admin
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<BasketResponseDTO> getAll(Long page, Long size) {
        Page<Basket> all = basketRepository.findAll(PageRequest.of(page.intValue(), size.intValue()));
        List<Basket> content = all.getContent();

        List<BasketResponseDTO> list = new ArrayList<>();
        for (Basket basket : content) {
            List<BasketProduct> allByBasketId = basketProductRepository.findAllByBasketId(basket.getId());
            BasketResponseDTO parse = parse(basket, allByBasketId);
            list.add(parse);
        }
        return list;
    }

    public BasketResponseDTO getUserProduct(UUID userId){
        Basket basket = basketRepository.getBasketByUser_Id(userId);
        List<BasketProduct> allByBasketId = basketProductRepository.findAllByBasketId(basket.getId());
        return parse(basket, allByBasketId);
    }

    public List<BasketProductDTO> parse(List<BasketProduct> basketProduct) {
        List<BasketProductDTO> list = new ArrayList<>();
        for (BasketProduct product : basketProduct) {
            BasketProductDTO basketProductDTO =  new BasketProductDTO(product.getId(),product.getProduct().getId(), product.getProduct().getName(), product.getProduct().getDescription(), product.getProduct().getPrice(), product.getProduct().getCount());
            list.add(basketProductDTO);
        }
        return list;
    }

    @Override
    public BaseResponse<BasketResponseDTO> create(UUID userId, UUID productId, int count) {
        User user = userService.findById(userId);
        Optional<Basket> optionalBasket = basketRepository.findBasketByUserId(userId);// shu userni savati bormi yuqmi dedik
        Product product = productRepository.findById(productId).orElseThrow(()->new DataNotFoundException("Product not found"));
        if (optionalBasket.isPresent()){
            Basket basket = optionalBasket.get();
            Optional<BasketProduct> pr = basketProductRepository.findByBasketIdAndProductId(basket.getId(), productId);
            BasketProduct basketProduct;
            if (pr.isPresent()) {
                basketProduct = pr.get();
                basketProduct.setCount(count);
            }else {
                basketProduct = new BasketProduct(basket, product, count);
            }
            basketProductRepository.save(basketProduct);
            return BaseResponse.<BasketResponseDTO>builder()
                    .message("success")
                    .code(200)
                    .data(parse(basket,List.of(basketProduct)))
                    .success(true)
                    .build();
        }else {
            Basket basket = new Basket();
            basket.setUser(user);
            basket.setCountProduct(count);
            basketRepository.save(basket);
            BasketProduct basketProduct = new BasketProduct(basket,product,count);
            basketProductRepository.save(basketProduct);
            return BaseResponse.<BasketResponseDTO>builder()
                    .code(200)
                    .success(true)
                    .data(parse(basket,List.of(basketProduct)))
                    .message("success")
                    .build();
        }
    }

    public BasketResponseDTO parse(Basket basket, List<BasketProduct> list){
        double price = 0d;
        for (BasketProduct basketProduct : list) {
            price += basketProduct.getProduct().getPrice();
        }
        return  new BasketResponseDTO(basket.getUser().getId(), basket.getId(),parse(list),price );
    }

}
