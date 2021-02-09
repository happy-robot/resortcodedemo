package kz.kaps.resort.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class ResourceHandlerConfig implements WebMvcConfigurer {

    @Autowired
    Environment env;

    private final static String IMAGES_URL = "/images";

    public final static String EXTRA_SMALL_IMAGES_URL = IMAGES_URL + "/95x65/";
    public final static String SMALL_IMAGES_URL = IMAGES_URL + "/160x120/";
    public final static String MEDIUM_IMAGES_URL = IMAGES_URL + "/400x300/";
    public final static String BIG_IMAGES_URL = IMAGES_URL + "/600x450/";
    public final static String FULL_IMAGES_URL = IMAGES_URL + "/1200x900/";

    public final static String LOCALITIES_URL = IMAGES_URL + "/localities/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("favicon.ico")
                .addResourceLocations("classpath:/static/favicon.ico");
        registry
                .addResourceHandler("/unify/**")
                .addResourceLocations("classpath:/static/unify/");
        registry
                .addResourceHandler("/assets/img/**")
                .addResourceLocations("classpath:/static/unify/assets/img/");
        registry
                .addResourceHandler("/assets/img-temp/**")
                .addResourceLocations("classpath:/static/unify/assets/img-temp/");
        registry
                .addResourceHandler("/stylesheets/**")
                .addResourceLocations("classpath:/static/stylesheets/");
        registry
                .addResourceHandler("/javascripts/**")
                .addResourceLocations("classpath:/static/javascripts/");
        registry
                .addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/build/");
        registry
                .addResourceHandler("/assets/img/**")
                .addResourceLocations("classpath:/static/build/img/");

        String imgFolder = env.getProperty("img.folder");

        registry
                .addResourceHandler(IMAGES_URL + "/**")
                .addResourceLocations(//"classpath:/static/",
                        "file:" + imgFolder);
    }

}
