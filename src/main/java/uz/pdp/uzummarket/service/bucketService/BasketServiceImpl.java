package uz.pdp.uzummarket.service.bucketService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BasketProductDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BasketResponseDTO;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDTO;
import uz.pdp.uzummarket.entity.*;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.BasketProductRepository;
import uz.pdp.uzummarket.repository.BasketRepository;
import uz.pdp.uzummarket.repository.ProductRepository;
import uz.pdp.uzummarket.service.productPhotosService.ProductPhotosService;
import uz.pdp.uzummarket.service.productService.ProductService;
import uz.pdp.uzummarket.service.userService.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ProductPhotosService productPhotosService;
    private final BasketProductRepository basketProductRepository;

    @Override
    public BasketResponseDTO getById(UUID basketId) {
        Basket basket = basketRepository.getById(basketId);
        List<BasketProduct> allByBasketId = basketProductRepository.findAllByBasketId(basket.getId());
        List<BasketProductDTO> parse = parse(allByBasketId);

        double price = 0d;
        for (BasketProductDTO basketProductDTO : parse) {
            price += basketProductDTO.getPrice();
        }
        return new BasketResponseDTO(basket.getUser().getId(), basket.getId(),parse,price);
    }

    @Override
    public List<BasketResponseDTO> getAll(Long page, Long size) {
        Page<Basket> all = basketRepository.findAll(PageRequest.of(page.intValue(), size.intValue()));
        List<Basket> content = all.getContent();

        List<BasketResponseDTO> list = new ArrayList<>();
        for (Basket basket : content) {
            List<BasketProduct> allByBasketId = basketProductRepository.findAllByBasketId(basket.getId());
            List<BasketProductDTO> parse = parse(allByBasketId);

            double price = 0d;
            for (BasketProductDTO basketProductDTO : parse) {
                price += basketProductDTO.getPrice();
            }
            BasketResponseDTO basketDTO = new BasketResponseDTO(basket.getUser().getId(),basket.getId(),parse,price);
            list.add(basketDTO);
        }

        return list;
    }

//    public ProductResponseDTO parse(Product product) {
//        ProductResponseDTO dto = new ProductResponseDTO();
//        dto.setId(product.getId());
//        dto.setDescription(product.getDescription());
//        dto.setName(product.getName());
//        dto.setCount(product.getCount());
//        dto.setPrice(product.getPrice());
//        dto.setCategoryId(product.getCategory().getId());
//        List<ProductPhotos> byProductId = productPhotosService.getByProductId(product.getId());
//        List<UUID> uuids = new ArrayList<>();
//        for (ProductPhotos productPhotos : byProductId) {
//            uuids.add(productPhotos.getId());
//        }
//        dto.setPhotos(uuids);
//        return dto;
//    }
    public BasketResponseDTO getUserProduct(UUID userId){
        Basket basket = basketRepository.getBasketByUser_Id(userId);
        BasketResponseDTO basketResponseDTO = new BasketResponseDTO();

        List<BasketProduct> allByBasketId = basketProductRepository.findAllByBasketId(basket.getId());
        List<BasketProductDTO> parse = parse(allByBasketId);
        double price = 0d;
        for (BasketProductDTO basketProductDTO : parse) {
            price += basketProductDTO.getPrice();
        }

        basketResponseDTO.setProducts(parse);
        basketResponseDTO.setUserId(basket.getUser().getId());
        basketResponseDTO.setBasketId(basket.getId());
        basketResponseDTO.setTotalPrice(price);

        return basketResponseDTO;
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
    public void create(UUID userId, UUID productId, int count) {
        User user = userService.findById(userId);
        Optional<Basket> optionalBasket = basketRepository.findBasketByUserId(userId);// shu userni savati bormi yuqmi dedik
        Product product = productRepository.findById(productId).orElseThrow(()->new DataNotFoundException("Product not found"));
        if (optionalBasket.isPresent()){
            Basket basket = optionalBasket.get();
            Optional<BasketProduct> pr = basketProductRepository.findByBasketIdAndProductId(basket.getId(), productId);
            if (pr.isPresent()) {
                BasketProduct basketProduct = pr.get();
                basketProduct.setCount(basketProduct.getCount() + count);
                basketProductRepository.save(basketProduct);
            }else {
                BasketProduct basketProduct = new BasketProduct(basket, product, count);
                basketProductRepository.save(basketProduct);
            }

        }else {
            Basket basket = new Basket();
            basket.setUser(user);
            basket.setCountProduct(count);
            basketRepository.save(basket);
            BasketProduct basketProduct = new BasketProduct(basket,product,count);
            basketProductRepository.save(basketProduct);
        }
    }

}
