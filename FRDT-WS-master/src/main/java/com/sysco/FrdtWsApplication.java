package com.sysco;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import com.sysco.common.Constants;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration
@MapperScan("com.sysco.dao")
public class FrdtWsApplication {

	public static void main(String[] args) {
		// default env is dev
				String env = Constants.DEV_ENVIRONMENT;
				// read from the environment variables passed from GO CD
				if (!StringUtils.isEmpty(System.getenv("APPLICATION_ENV"))) {
					env = System.getenv("APPLICATION_ENV");
				}
				log.info("Starting application with {} configuration!", env);
				new SpringApplicationBuilder(FrdtWsApplication.class).profiles(env).run(args);
	}
}
