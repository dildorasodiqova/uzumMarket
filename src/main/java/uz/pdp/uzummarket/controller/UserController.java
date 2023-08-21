package uz.pdp.uzummarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uzummarket.Dto.requestSTO.SignInDTO;
import uz.pdp.uzummarket.Dto.requestSTO.UserCreateDTO;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
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
    public ResponseEntity<BaseResponse<UserResponseDTO>> add(@RequestBody UserCreateDTO userCreateDTO){
        return ResponseEntity.ok(userService.create(userCreateDTO));
    }


}
