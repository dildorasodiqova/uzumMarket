package uz.pdp.uzummarket.service.userService;

import uz.pdp.uzummarket.entity.User;
import uz.pdp.uzummarket.Dto.requestSTO.UserCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDTO create(UserCreateDTO userCreateDTO);

    List<UserResponseDTO> getAll(Long startPage, Long endPage);

    User findById(UUID userId);
}
