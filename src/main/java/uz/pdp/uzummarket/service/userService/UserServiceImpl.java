package uz.pdp.uzummarket.service.userService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.Dto.requestSTO.SignInDTO;
import uz.pdp.uzummarket.entity.User;
import uz.pdp.uzummarket.enums.Gender;
import uz.pdp.uzummarket.enums.UserRole;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.UserRepository;
import uz.pdp.uzummarket.Dto.requestSTO.UserCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.UserResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Override
    public UserResponseDTO create(UserCreateDTO userCreateDTO) {
        User user = createUser(userCreateDTO);
//        User map = modelMapper.map(userCreateDTO, User.class);
        User save = userRepository.save(user);
        return   modelMapper.map(save, UserResponseDTO.class);
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
    public List<UserResponseDTO> getAll(Long page, Long size) {
          PageRequest pageRequest = PageRequest.of(page.intValue(), size.intValue());
        Page<User> all = userRepository.findAll(pageRequest);
        List<UserResponseDTO> list = new ArrayList<>();
        for (User user : all.getContent()) {
            UserResponseDTO map = modelMapper.map(user, UserResponseDTO.class);
            list.add(map);
        }
        return list;
    }

    @Override
    public User findById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public UserResponseDTO signIn(SignInDTO dto) {
      User user =  userRepository.getUserByEmailAndPasswordAndFirstName(dto.getEmail(), dto.getPassword(), dto.getName())
              .orElseThrow(()-> new DataNotFoundException("User not found"));
        return createUserResponseDTO(user);
    }

    private UserResponseDTO createUserResponseDTO(User user) {
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
