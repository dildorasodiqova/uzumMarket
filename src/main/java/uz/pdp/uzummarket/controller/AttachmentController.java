package uz.pdp.uzummarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.uzummarket.entity.Attachment;
import uz.pdp.uzummarket.service.attachmentService.AttachmentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/multiple-upload")
    public ResponseEntity<List<UUID>> multipleUpload(@RequestParam("files") MultipartFile[] files) throws IOException {
        List<UUID> fileIdList = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            UUID uuid = attachmentService.uploadImage(file);
            fileIdList.add(uuid);
        }
        return ResponseEntity.status(HttpStatus.OK).body(fileIdList);
    }

    @PostMapping("/single-upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        UUID uploadImage = attachmentService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> downloadImage(@PathVariable UUID fileId) {
        Attachment attachment = attachmentService.downloadImage(fileId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf(attachment.getContentType()))
                .body(attachment.getBytes());
    }
}