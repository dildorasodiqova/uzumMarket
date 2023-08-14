package uz.pdp.uzummarket.service.attachmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.uzummarket.entity.Attachment;
import uz.pdp.uzummarket.repository.AttachmentRepository;
import uz.pdp.uzummarket.util.ImageUtils;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        Attachment attachment = attachmentRepository.save(Attachment.builder()
                .name(file.getOriginalFilename())
                .contentType(file.getContentType())
                .imageDate(ImageUtils.compressImage(file.getBytes())).build());
        if (attachment != null) {
            return "file upload successfully";
        }
        return null;
    }

    @Override
    public byte[] downloadImage(String fileName) {
        Optional<Attachment>  dbAttachment = attachmentRepository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(dbAttachment.get().getImageDate());
        return images;
    }
}
