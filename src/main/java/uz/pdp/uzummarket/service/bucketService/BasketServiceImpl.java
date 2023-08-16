package uz.pdp.uzummarket.service.bucketService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.ProductCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BasketResponseDTO;
import uz.pdp.uzummarket.entity.Basket;
import uz.pdp.uzummarket.entity.Category;
import uz.pdp.uzummarket.entity.Product;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.BasketRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final ModelMapper modelMapper;

    @Override
    public BasketResponseDTO getById(UUID basketId) {
        Basket byId = basketRepository.getById(basketId);
        List<Product> products = byId.getProducts();
        List<ProductCreateDTO> list = new ArrayList<>();
        for (Product product : products) {
            ProductCreateDTO productRequestDto = new ProductCreateDTO();
            productRequestDto.setCategoryId(product.getCategory().getId());
            productRequestDto.setName(product.getName());
            productRequestDto.setCount(product.getCount());
            productRequestDto.setPrice(product.getPrice());
            productRequestDto.setDescription(product.getDescription());
            list.add(productRequestDto);
        }

        BasketResponseDTO basketDTO = new BasketResponseDTO();
        basketDTO.setProducts(list);
        basketDTO.setId(byId.getId());
        basketDTO.setUserId(byId.getUser().getId());

        if (basketDTO == null){
            new DataNotFoundException("Category not found");
        }
            return basketDTO;
    }

    @Override
    public List<BasketResponseDTO> getAll(Long page, Long size) {
        Page<Basket> all = basketRepository.findAll(PageRequest.of(page.intValue(), size.intValue()));
        List<Basket> content = all.getContent();
        List<ProductCreateDTO> productRequestDtos = new ArrayList<>();
        List<BasketResponseDTO> list = new ArrayList<>();
        for (Basket basket : content) {
            for (Product product : basket.getProducts()) {
                ProductCreateDTO product1 = new ProductCreateDTO();
                product1.setDescription(product.getDescription());
                product1.setName(product.getName());
                product1.setCount(product.getCount());
                product1.setPrice(product.getPrice());
                product1.setCategoryId(product.getCategory().getId());
                productRequestDtos.add(product1);
            }
            BasketResponseDTO basketDTO = new BasketResponseDTO();
            basketDTO.setProducts(productRequestDtos);
            basketDTO.setUserId(basket.getUser().getId());
            list.add(basketDTO);
        }

        return list;
    }
}
