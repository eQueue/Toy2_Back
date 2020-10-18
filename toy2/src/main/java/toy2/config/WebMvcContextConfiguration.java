package toy2.config;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = { "toy2.controller", "toy2.service", "toy2.dao","toy2.config","toy2.config.security" })
public class WebMvcContextConfiguration implements WebMvcConfigurer{
 
    // default servlet handler를 사용하게 합니다.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
        
    }
    
    //Swagger config
    @Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any()) // // RequestMapping으로 할당된 모든 URL 리스트를 검색하여 인식
				.paths(PathSelectors.ant("/**"))// PathSelectors.any() 를 할경우 Web api가 아닌 경로도 api path로 인식한다. .ant() 로 api 로사용할 경로를 넣어준다.
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(true);
				
	}
    
    private ApiInfo apiInfo() {
    	//현재 swagger version : 2.9.2
        Contact contact = new Contact("이규홍", "https://github.com/eQueue", "latancy486@naver.com");
        List<VendorExtension> vendorExtensions = new ArrayList<>();
        ApiInfo apiInfo = new ApiInfo("Guess Me API", "Show API", "v1.0", "localhost:8080/toy2/swagger-ui.html", contact, "", "");
        return apiInfo;
    }
   
}