package pnu.cse.cloudchain.auth.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3UploadService {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String base64ImageUpload(String base64, String userId) throws IOException {
        // Extract actual Base64 string by splitting the original string
        String[] parts = base64.split(",");

        String imageString = parts[1];

        byte[] imageData = Base64.decodeBase64(imageString);
        String fileName = getCurrentTimeAsString() + "_" + userId;
        log.info("Check for imageData in base64ImageUpload = {}", imageData.toString());
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
        if (image == null) {
            log.error("Could not decode image");
            return null; // or throw an exception
        } else {
            log.error(image.toString());
        }

        // Create a temporary file to store the image
        File tempFile = File.createTempFile("temp", ".png");
        ImageIO.write(image, "png", tempFile);

        String savedUri = putS3(tempFile, fileName);

        // Delete the temporary file
        tempFile.delete();

        return savedUri;
    }


    public String base64FileOrVideoUpload(String base64,String userId) throws IOException {
        byte[] fileData = Base64.decodeBase64(base64);
        String fileName = getCurrentTimeAsString() + "_" + userId;
        File file = new File(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(fileData);
        fileOutputStream.close();
        String uploadImageUrl = putS3(file,fileName);
        removeNewFile(file);
        return uploadImageUrl;
    }

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String multipartFileUpload(MultipartFile multipartFile, String userId) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
        String fileName = getCurrentTimeAsString()+"_"+userId;
        log.info("Check for imageData in multipartFileUpload = {}", uploadFile.toString());

        return fileUpload(uploadFile, fileName);
    }

    private String fileUpload(File uploadFile, String fileName){
        log.warn(fileName);
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile); //로컬에 생성된 File 삭제 (MultipartFile -> File 전환하며 로컬에 파일 생성됨)
        return uploadImageUrl;
    }

    private String putS3(File uploadFile,String fileName){
        log.info("Check for puts3 ={}",fileName);
        amazonS3Client.putObject(
                new PutObjectRequest(bucket,fileName,uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)); // PublicRead 권한으로 업로드 됨
        return amazonS3Client.getUrl(bucket,fileName).toString();
    }


    private void removeNewFile(File targetFile){
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        log.info(file.getOriginalFilename());
        try (OutputStream os = new FileOutputStream(convertFile)) {
            os.write(file.getBytes());
        }
        return Optional.of(convertFile);
    }
    private String getCurrentTimeAsString(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }
}
