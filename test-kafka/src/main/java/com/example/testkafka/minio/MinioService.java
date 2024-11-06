package com.example.testkafka.minio;

import com.example.testkafka.file_system.MinioResource;
import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;

    public void uploadFile(MinioResource minioResource) throws Exception {
        if (minioResource.isBase()) {
            uploadFile(Buckets.BASE_BUCKET, minioResource);
        }
        else {
            uploadFile(Buckets.DEFAULT_BUCKET, minioResource);
        }
    }

    private void uploadFile(String bucket, MinioResource minioResource) throws Exception {
        var data = new ByteArrayInputStream(minioResource.getData());
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(minioResource.getPath() + "/" + minioResource.getName())
                .stream(data, minioResource.getData().length, -1)
                .build());
    }

    public byte[] downloadFile(String objectName, boolean isBase) throws Exception {
        if (isBase) {
            return downloadFile(objectName, Buckets.BASE_BUCKET);
        }
        return downloadFile(objectName, Buckets.DEFAULT_BUCKET);
    }

    private byte[] downloadFile(String objectName, String bucket) throws Exception {
        var stream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(objectName)
                .build());
        return stream.readAllBytes();
    }


    public void deletePath(MinioResource minioResource) {
        if (minioResource.isBase()) {
            deletePathObjects(Buckets.BASE_BUCKET, minioResource);
        }
        else {
            deletePathObjects(Buckets.DEFAULT_BUCKET, minioResource);
        }
    }

    public void deleteFile(MinioResource minioResource) throws Exception {
        if (minioResource.isBase()) {
            deleteFileByBucket(Buckets.BASE_BUCKET, createMinioObjectName(minioResource));
        }
        else {
            deleteFileByBucket(Buckets.DEFAULT_BUCKET, createMinioObjectName(minioResource));
        }
    }

    private void deletePathObjects(String bucket, MinioResource minioResource) {
        var foundObjects = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(bucket)
                .prefix(createMinioObjectName(minioResource))
                .recursive(true)
                .build());

        foundObjects.forEach(foundObject -> {
            try {
                deleteFileByBucket(bucket, foundObject.get().objectName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void deleteFileByBucket(String bucket, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(objectName)
                .build());
    }

    private String createMinioObjectName(MinioResource minioResource) {
        return minioResource.getPath() + "/" + minioResource.getName();
    }

}
