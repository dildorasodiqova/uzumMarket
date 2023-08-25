package uz.pdp.uzummarket.service.attachmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.entity.Attachment;
import uz.pdp.uzummarket.exception.DataNotFoundException;
import uz.pdp.uzummarket.repository.AttachmentRepository;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Override
    public BaseResponse<UUID> uploadImage(MultipartFile file) throws IOException {
        Attachment attachment = attachmentRepository.save(Attachment.builder()
                .name(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .bytes(file.getBytes()).build());

        return BaseResponse.<UUID>builder()
                .code(200)
                .success(true)
                .message("success")
                .data(attachment.getId())
                .build();

    }

    @Override
    public Attachment downloadImage(UUID fileId) throws RuntimeException {
        return attachmentRepository.findById(fileId).orElseThrow(()-> new DataNotFoundException("Attachment not found"));
    }
}
