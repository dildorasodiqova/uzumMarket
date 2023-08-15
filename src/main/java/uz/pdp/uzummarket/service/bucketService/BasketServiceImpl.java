package uz.pdp.uzummarket.service.bucketService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.BasketDTO;
import uz.pdp.uzummarket.Dto.requestSTO.CategoryDTO;
import uz.pdp.uzummarket.Dto.requestSTO.ProductRequestDto;
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
    public BasketDTO getById(UUID basketId) {
        Basket byId = basketRepository.getById(basketId);
        List<Product> products = byId.getProducts();
        List<ProductRequestDto> list = new ArrayList<>();
        for (Product product : products) {
            ProductRequestDto productRequestDto = new ProductRequestDto();
            productRequestDto.setCategoryId(product.getCategory().getId());
            productRequestDto.setName(product.getName());
            productRequestDto.setCount(product.getCount());
            productRequestDto.setPrice(product.getPrice());
            productRequestDto.setDescription(product.getDescription());
            list.add(productRequestDto);
        }

        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setProducts(list);
        basketDTO.setUserId(byId.getUser().getId());

        if (basketDTO == null){
            new DataNotFoundException("Category not found");
        }
            return basketDTO;
    }

    @Override
    public List<BasketDTO> getAll(Long page, Long size) {
        Page<Basket> all = basketRepository.findAll(PageRequest.of(page.intValue(), size.intValue()));
        List<Basket> content = all.getContent();
        List<ProductRequestDto> productRequestDtos = new ArrayList<>();
        List<BasketDTO> list = new ArrayList<>();
        for (Basket basket : content) {
            for (Product product : basket.getProducts()) {
                ProductRequestDto product1 = new ProductRequestDto();
                product1.setDescription(product.getDescription());
                product1.setName(product.getName());
                product1.setCount(product.getCount());
                product1.setPrice(product.getPrice());
                product1.setCategoryId(product.getCategory().getId());
                productRequestDtos.add(product1);
            }
            BasketDTO basketDTO = new BasketDTO();
            basketDTO.setProducts(productRequestDtos);
            basketDTO.setUserId(basket.getUser().getId());
            list.add(basketDTO);
        }

        return list;
    }
}
