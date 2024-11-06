package com.example.testkafka.minio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class Buckets {
    public static final String DEFAULT_BUCKET = "delta-files";

    public static final String BASE_BUCKET = "delta-files-base";
}
