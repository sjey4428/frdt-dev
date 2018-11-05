package com.sysco.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AWS3Config {
	
	/** The access key. */
	@Value("${amazon.aws.accesskey}")
	private String accessKey;
	
	/** The secret key. */
	@Value("${amazon.aws.secretkey}")
	private String secretKey;

	@Bean
	public AmazonS3 amazonS3() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		return AmazonS3ClientBuilder.standard()
				.withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.build();
	}

}
