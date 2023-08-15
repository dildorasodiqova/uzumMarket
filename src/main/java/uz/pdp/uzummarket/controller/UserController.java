package uz.pdp.uzummarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.uzummarket.Dto.requestSTO.UserDTO;
import uz.pdp.uzummarket.Dto.responceDTO.UserResponseDTO;
import uz.pdp.uzummarket.service.userService.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/add")
    public ResponseEntity<UserResponseDTO> add(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.create(userDTO));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponseDTO>> getAll( @RequestParam(defaultValue = "0") Long page,
                                                         @RequestParam(defaultValue = "10") Long size){
        return ResponseEntity.ok(userService.getAll(page,size));
    }

}
