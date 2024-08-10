package org.example.deltawebfacade.constants;

import java.util.*;

public class MediaTypeAssociation {

    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_GIF = "image/gif";

    public static final String VIDEO_MP4 = "video/mp4";
    public static final String VIDEO_MKV = "video/mkv";
    public static final String VIDEO_QUICK = "video/quicktime";

    public static final String AUDIO_MP3 = "audio/mp3";
    public static final String AUDIO_WAV = "audio/wav";
    public static final String AUDIO_MPEG = "audio/mpeg";

    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_PDF = "application/pdf";
    public static final String TEXT_OCTET = "application/octet-stream";

    public static final String APPLICATION_EXE = "application/exe";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";

    public static Map<Integer, List<String>> fileTypes = new HashMap<>();
    public static List<Integer> integerTypes = new ArrayList<>();

    static {
        List<String> photoTypes = new ArrayList<>();
        photoTypes.add(IMAGE_JPEG);
        photoTypes.add(IMAGE_PNG);
        photoTypes.add(IMAGE_GIF);
        fileTypes.put(1, photoTypes);

        List<String> videoTypes = new ArrayList<>();
        videoTypes.add(VIDEO_MP4);
        videoTypes.add(VIDEO_MKV);
        videoTypes.add(VIDEO_QUICK);
        fileTypes.put(2, videoTypes);

        List<String> audioTypes = new ArrayList<>();
        audioTypes.add(AUDIO_MP3);
        audioTypes.add(AUDIO_WAV);
        audioTypes.add(AUDIO_MPEG);
        fileTypes.put(3, audioTypes);

        List<String> textDocumentTypes = new ArrayList<>();
        textDocumentTypes.add(TEXT_PLAIN);
        textDocumentTypes.add(TEXT_HTML);
        textDocumentTypes.add(TEXT_PDF);
        textDocumentTypes.add(TEXT_OCTET);
        fileTypes.put(4, textDocumentTypes);

        List<String> programDocumentTypes = new ArrayList<>();
        programDocumentTypes.add(APPLICATION_EXE);
        programDocumentTypes.add(APPLICATION_JSON);
        programDocumentTypes.add(APPLICATION_XML);
        fileTypes.put(5, programDocumentTypes);

        integerTypes = Arrays.asList(1, 2, 3, 4, 5);
    }
}