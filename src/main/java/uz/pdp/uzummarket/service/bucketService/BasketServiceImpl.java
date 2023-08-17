package uz.pdp.uzummarket.service.bucketService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BasketResponseDTO;
import uz.pdp.uzummarket.Dto.responceDTO.ProductResponseDTO;
import uz.pdp.uzummarket.entity.Basket;
import uz.pdp.uzummarket.entity.Product;
import uz.pdp.uzummarket.entity.ProductPhotos;
import uz.pdp.uzummarket.entity.User;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.BasketRepository;
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
    private final ProductService productService;
    private final ProductPhotosService productPhotosService;

    @Override
    public BasketResponseDTO getById(UUID basketId) {
        Basket byId = basketRepository.getById(basketId);
        Product product = byId.getProducts();
        List<ProductCreateDTO> list = new ArrayList<>();

        ProductCreateDTO productRequestDto = new ProductCreateDTO();
        productRequestDto.setCategoryId(product.getCategory().getId());
        productRequestDto.setName(product.getName());
        productRequestDto.setCount(product.getCount());
        productRequestDto.setPrice(product.getPrice());
        productRequestDto.setDescription(product.getDescription());
        list.add(productRequestDto);

        BasketResponseDTO basketResponseDTO = responseDTOParse(byId);
        return basketResponseDTO;
    }

    @Override
    public List<BasketResponseDTO> getAll(Long page, Long size) {
        Page<Basket> all = basketRepository.findAll(PageRequest.of(page.intValue(), size.intValue()));
        List<Basket> content = all.getContent();

        List<BasketResponseDTO> responseDTOS = new ArrayList<>();
        for (Basket basket : content) {
            BasketResponseDTO basketDTO = new BasketResponseDTO();
            ProductResponseDTO parse = parse(basket.getProducts());

            basketDTO.setProduct(parse);
            basketDTO.setUserId(basket.getUser().getId());
            responseDTOS.add(basketDTO);
        }

        return responseDTOS;
    }

    public ProductResponseDTO parse(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setDescription(product.getDescription());
        dto.setName(product.getName());
        dto.setCount(product.getCount());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategory().getId());
        List<ProductPhotos> byProductId = productPhotosService.getByProductId(product.getId());
        List<UUID> uuids = new ArrayList<>();
        for (ProductPhotos productPhotos : byProductId) {
            uuids.add(productPhotos.getId());
        }
        dto.setPhotos(uuids);
        return dto;
    }
    public List<BasketResponseDTO> getUserProduct(UUID userId){
        List<Basket> basketByUserId = basketRepository.getBasketByUser_Id(userId);
        List<BasketResponseDTO> list =  new ArrayList<>();
        for (Basket basket : basketByUserId) {
            BasketResponseDTO basketResponseDTO = responseDTOParse(basket);
            list.add(basketResponseDTO);
        }

        return list;

    }

    @Override
    public BasketResponseDTO create(UUID userId, UUID productId, int count) {
        Optional<Basket> byUserIdAndProductsId = basketRepository.findByUser_idAndProducts_id(userId, productId);
        Basket basket;
        if (byUserIdAndProductsId.isPresent()) {
            basket = byUserIdAndProductsId.get();
            basket.setCount(basket.getCount() + count);
        } else {
            basket = new Basket();
            basket.setCount(count);
            User user = userService.findById(userId);
            basket.setUser(user);
            Product product = productService.getById(productId);
            basket.setProducts(product);
        }
        Basket save = basketRepository.save(basket);
        return   responseDTOParse(save);

    }
    public BasketResponseDTO responseDTOParse(Basket basket){
        BasketResponseDTO basketResponseDTO = new BasketResponseDTO();
        Product products = basket.getProducts();
        ProductResponseDTO parse = parse(products);
        basketResponseDTO.setProduct(parse);
        basketResponseDTO.setId(basket.getId());
        basketResponseDTO.setUserId(basket.getUser().getId());
        return basketResponseDTO;
    }
}
