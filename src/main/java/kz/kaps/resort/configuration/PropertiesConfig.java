package kz.kaps.resort.configuration;


import kz.kaps.resort.core.properties.ImageProperties;
import kz.kaps.resort.propertiesproviders.ImagePropertiesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class PropertiesConfig {

    @Autowired
    Environment env;

    @Bean
    public ImageProperties getImageProperties(){
        String folderForExtraSmallImgs = env.getProperty("img.folder.extrasmall");
        String folderForSmallImgs = env.getProperty("img.folder.small");
        String folderForMediumImgs = env.getProperty("img.folder.medium");
        String folderForBigImgs = env.getProperty("img.folder.big");
        String folderForFullImgs = env.getProperty("img.folder.full");
        String folderForOriginalImgs = env.getProperty("img.folder.original");

        ImagePropertiesProvider propertiesProvider = new ImagePropertiesProvider();

        propertiesProvider.setFolderForExtraSmallImgs(folderForExtraSmallImgs);
        propertiesProvider.setFolderForSmallImgs(folderForSmallImgs);
        propertiesProvider.setFolderForMediumImgs(folderForMediumImgs);
        propertiesProvider.setFolderForBigImgs(folderForBigImgs);
        propertiesProvider.setFolderForFullImgs(folderForFullImgs);
        propertiesProvider.setFolderForOriginalImgs(folderForOriginalImgs);

        return propertiesProvider;
    }

}
