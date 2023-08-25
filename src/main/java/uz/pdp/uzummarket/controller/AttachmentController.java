package uz.pdp.uzummarket.controller;

import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.entity.Attachment;
import uz.pdp.uzummarket.service.attachmentService.AttachmentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@MultipartConfig(maxFileSize = 5000000, maxRequestSize = 20, fileSizeThreshold = 1024)

public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/multiple-upload")
    public ResponseEntity<BaseResponse<List<UUID>>> multipleUpload(@RequestParam("files") MultipartFile[] files) throws IOException {
        List<UUID> fileIdList = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            BaseResponse<UUID> uuid = attachmentService.uploadImage(file);
            fileIdList.add(uuid.getData());
        }
        return ResponseEntity.ok(BaseResponse.<List<UUID>>builder()
                .data(fileIdList)
                .message("success")
                .code(200)
                .success(true)
                .build());
    }

    @PostMapping("/single-upload")
    public ResponseEntity<BaseResponse<UUID>> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(attachmentService.uploadImage(file));
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable UUID fileId) {
        Attachment attachment = attachmentService.downloadImage(fileId);

        return ResponseEntity.ok(attachment.getBytes());
    }
}