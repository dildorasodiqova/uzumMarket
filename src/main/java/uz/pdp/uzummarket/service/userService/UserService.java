package uz.pdp.uzummarket.service.userService;

import uz.pdp.uzummarket.Dto.requestSTO.SignInDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.entity.User;
import uz.pdp.uzummarket.Dto.requestSTO.UserCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    BaseResponse<UserResponseDTO> create(UserCreateDTO userCreateDTO);

    BaseResponse<List<UserResponseDTO>> getAll(Long startPage, Long endPage);

    BaseResponse<User> findById(UUID userId);

    BaseResponse<UserResponseDTO> signIn(SignInDTO dto);

    BaseResponse<UserResponseDTO> getById(UUID userId);
}
