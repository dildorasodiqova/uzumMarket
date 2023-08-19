package uz.pdp.uzummarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uzummarket.Dto.requestSTO.SignInDTO;
import uz.pdp.uzummarket.Dto.requestSTO.UserCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.UserResponseDTO;
import uz.pdp.uzummarket.service.userService.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/add")
    public ResponseEntity<UserResponseDTO> add(@RequestBody UserCreateDTO userCreateDTO){
        return ResponseEntity.ok(userService.create(userCreateDTO));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponseDTO>> getAll( @RequestParam(defaultValue = "0") Long page,
                                                         @RequestParam(defaultValue = "10") Long size){
        return ResponseEntity.ok(userService.getAll(page,size));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserResponseDTO> signIn(@RequestBody SignInDTO dto) {
        return ResponseEntity.ok(userService.signIn(dto));
    }

    @PostMapping("/get-by-id")
    public ResponseEntity<UserResponseDTO> getById(@RequestBody UUID userId){
        return ResponseEntity.ok(userService.getById(userId));
    }

}
