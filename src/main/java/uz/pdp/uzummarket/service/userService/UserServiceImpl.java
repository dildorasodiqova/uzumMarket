package uz.pdp.uzummarket.service.userService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.pdp.uzummarket.entity.User;
import uz.pdp.uzummarket.repository.UserRepository;
import uz.pdp.uzummarket.requestSTO.UserDTO;
import uz.pdp.uzummarket.responceDTO.UserResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Override
    public UserResponseDTO create(UserDTO userDTO) {
        User map = modelMapper.map(userDTO, User.class);
        User save = userRepository.save(map);
        return   modelMapper.map(save, UserResponseDTO.class);
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
}
