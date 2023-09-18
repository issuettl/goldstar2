/**
 * 
 */
package kr.co.lge.goldstar;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;

/**
 * @author issuettl
 *
 */
@SpringBootApplication
@EnableScheduling
public class GoldStarApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("##### GoldBootApplication Start #####");

		SpringApplication springApplication = new SpringApplication(GoldStarApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		//springApplication.setLogStartupInfo(false);
		
		String profile = System.getProperty("spring.profiles.active");
		if(!StringUtils.hasText(profile)) {
			System.setProperty("spring.profiles.active", "local");
		}
		
		
		springApplication.run(args);

		System.out.println("##### GoldBootApplication End #####");
	}

}
