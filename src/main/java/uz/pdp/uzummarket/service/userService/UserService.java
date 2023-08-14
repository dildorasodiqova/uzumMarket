package uz.pdp.uzummarket.service.userService;

import uz.pdp.uzummarket.entity.User;
import uz.pdp.uzummarket.repository.UserRepository;
import uz.pdp.uzummarket.requestSTO.UserDTO;
import uz.pdp.uzummarket.responceDTO.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDTO create(UserDTO userDTO);

    List<UserResponseDTO> getAll(Long startPage, Long endPage);

    User findById(UUID userId);
}
