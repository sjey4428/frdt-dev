package com.sysco.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.sysco.exception.AWS3Exception;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by james.zhu on 2018/8/27.
 */
public class AWS3Utils {
    private static Logger logger = Logger.getLogger(AWS3Utils.class);

    /**
     * get url
     * @param bucketName
     * @param remoteFileName
     * @param amazonS3
     * @return
     */
    public static String getUrlFromS3(String bucketName, String remoteFileName, AmazonS3 amazonS3){
        try {
            GeneratePresignedUrlRequest httpRequest=new GeneratePresignedUrlRequest(bucketName, remoteFileName);
            String url= amazonS3.generatePresignedUrl(httpRequest).toString();
            return url;
        } catch (Exception e) {
            logger.info("[AWS3Utils] getUrlFromS3 error: " + e.getMessage());
            throw new AWS3Exception(HttpStatus.BAD_REQUEST, "getUrlFromS3 error");
        }
    }

    public static void deleteFolder(String bucketName, String folderName, AmazonS3 client) {
        List<S3ObjectSummary> fileList = client.listObjects(bucketName, folderName).getObjectSummaries();
        for (S3ObjectSummary file : fileList) {
            client.deleteObject(bucketName, file.getKey());
        }
        client.deleteObject(bucketName, folderName);
    }

    public static List<String> getDirectList(ObjectListing objects, AmazonS3 client) {
        List<String> list = new ArrayList<>();
        String prefix = objects.getPrefix();
        do {
            List<String> commomprefix = objects.getCommonPrefixes();
            for (String comp : commomprefix) {
                String dirName = comp.substring(prefix == null ? 0 : prefix.length(), comp.length() - 1);
                list.add(dirName);
            }
            objects = client.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());
        return list;
    }

    public static ObjectListing getBacketObjects(String bucketName, String prefix, Boolean isDelimiter, AmazonS3 client) {
        if (bucketName == null || bucketName.isEmpty()) {
            return null;
        }
        ListObjectsRequest objectsRequest = new ListObjectsRequest().withBucketName(bucketName);
        if (prefix != null && !prefix.isEmpty()) {
            objectsRequest = objectsRequest.withPrefix(prefix);
        }
        if (isDelimiter) {
            objectsRequest = objectsRequest.withDelimiter("/");
        }
        ObjectListing objects = client.listObjects(objectsRequest);
        return objects;
    }
}
