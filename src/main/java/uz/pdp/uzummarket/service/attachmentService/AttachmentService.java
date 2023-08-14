package uz.pdp.uzummarket.service.attachmentService;

import org.springframework.web.multipart.MultipartFile;
import uz.pdp.uzummarket.entity.Attachment;

import java.io.IOException;

public interface AttachmentService {
    String uploadImage(MultipartFile file) throws IOException;
    byte[] downloadImage(String fileName);
}
