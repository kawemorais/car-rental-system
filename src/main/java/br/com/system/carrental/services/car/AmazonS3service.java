package br.com.system.carrental.services.car;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;


@Service
public class AmazonS3service {

    private final AmazonS3 amazonS3;

    @Value("${application.bucket.name}")
    private String bucketName;

    public AmazonS3service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String saveImageOnS3Bucket(MultipartFile file) throws IOException {

        String url;

        String fileName = UUID.randomUUID().toString().concat(getFileExtension(Objects.requireNonNull(file.getOriginalFilename())));
        InputStream fileInputStream = file.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, fileInputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead);

        try{
            amazonS3.putObject(putObjectRequest);

            url = amazonS3.getUrl(bucketName, fileName).toExternalForm();

        }catch (Error e){
            throw new IOException("Erro ao fazer upload do arquivo");
        }

        return url;

    }

    public void deleteImageOnS3Bucket(String fileName){
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        } catch (SdkClientException e){
            e.printStackTrace();
        }
    }

    private static String getFileExtension(String filename) {
        if (filename.contains("."))
            return filename.substring(filename.lastIndexOf("."));
        else
            return "";
    }

}
