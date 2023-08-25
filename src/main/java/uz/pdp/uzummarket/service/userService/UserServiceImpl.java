package uz.pdp.uzummarket.service.userService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.SignInDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.entity.User;
import uz.pdp.uzummarket.enums.Gender;
import uz.pdp.uzummarket.enums.UserRole;
import uz.pdp.uzummarket.exception.DataAlreadyExistsException;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.UserRepository;
import uz.pdp.uzummarket.Dto.requestSTO.UserCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.UserResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Override
    public BaseResponse<UserResponseDTO> create(UserCreateDTO userCreateDTO) {
        User user = createUser(userCreateDTO);
        Optional<User> byId = userRepository.findByEmail(user.getEmail());
        if (byId.isPresent()){
            throw new DataAlreadyExistsException("User already exists");
        }else {
            User save = userRepository.save(user);
            UserResponseDTO parse = parse(save);
            return BaseResponse.<UserResponseDTO>builder()
                    .code(200)
                    .data(parse)
                    .message("success")
                    .success(true)
                    .build();
        }

    }

    private User createUser(UserCreateDTO userCreateDTO) {
        return new User(
                userCreateDTO.getFirstName(),
                userCreateDTO.getLastName(),
                userCreateDTO.getPhoneNumber(),
                userCreateDTO.getEmail(),
                userCreateDTO.getPassword(),
                Gender.valueOf(userCreateDTO.getGender()),
                UserRole.USER,
                userCreateDTO.getBirthday()
        );
    }

    @Override
    public BaseResponse<List<UserResponseDTO>> getAll(Long page, Long size) {
          PageRequest pageRequest = PageRequest.of(page.intValue(), size.intValue());
        Page<User> all = userRepository.findAll(pageRequest);
        List<UserResponseDTO> list = new ArrayList<>();
        for (User user : all.getContent()) {
            UserResponseDTO map = modelMapper.map(user, UserResponseDTO.class);
            list.add(map);
        }
        return BaseResponse.<List<UserResponseDTO>>builder()
                .data(list)
                .code(200)
                .success(true)
                .message("success")
                .build();
    }

    @Override
    public BaseResponse<User> findById(UUID userId) {
        return BaseResponse.<User>builder()
                .success(true)
                .code(200)
                .data(userRepository.findById(userId).orElseThrow (() -> new DataNotFoundException("user not found")))
                .message("success")
                .build();
    }

    @Override
    public BaseResponse<UserResponseDTO> signIn(SignInDTO dto) {
      User user =  userRepository.getUserByEmailAndPasswordAndFirstName(dto.getEmail(), dto.getPassword(), dto.getName())
              .orElseThrow(()-> new DataNotFoundException("User not found"));
        return BaseResponse.<UserResponseDTO>builder()
                .data(parse(user))
                .message("success")
                .code(200)
                .success(true)
                .build();
    }


    @Override
    public BaseResponse<UserResponseDTO> getById(UUID userId) {
        BaseResponse<User> user = findById(userId);
        return BaseResponse.<UserResponseDTO>builder()
                .data(parse(user.getData()))
                .code(200)
                .message("success")
                .success(true)
                .build();
    }

    private UserResponseDTO parse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPassword(),
                user.getGender().toString(),
                user.getUserRole().toString(),
                user.getBirthDate()
        );
    }
}
