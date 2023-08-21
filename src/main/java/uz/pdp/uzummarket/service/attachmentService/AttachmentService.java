package uz.pdp.uzummarket.service.attachmentService;

import org.springframework.web.multipart.MultipartFile;
import uz.pdp.uzummarket.Dto.responceDTO.BaseResponse;
import uz.pdp.uzummarket.entity.Attachment;

import java.io.IOException;
import java.util.UUID;

public interface AttachmentService {
    BaseResponse<UUID> uploadImage(MultipartFile file) throws IOException;
    Attachment downloadImage(UUID fileId);
}
