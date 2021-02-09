package kz.kaps.resort.configuration;


import kz.kaps.resort.core.dictionaries.Locality;
import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.Image;
import kz.kaps.resort.entrypoints.html.landlord.dto.AdFormDto;
import kz.kaps.resort.dataproviders.sms.SMSServiceImpl;
import kz.kaps.resort.entrypoints.converters.HousingTypeConverter;
import kz.kaps.resort.entrypoints.html.ImageDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    //config for localization of JSR-303 validation messages
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    //registering message source of localized JSR-303 validation messages
    @Bean
    public LocalValidatorFactoryBean getValidator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login");
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    //serialize and deserialize SOAP XML requests for SMS
    @Bean
    public Jaxb2Marshaller smsMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("kz.kaps.resort.dataproviders.sms.soap.wsdl");
        return marshaller;
    }

    @Bean
    public SMSServiceImpl smsClient(Jaxb2Marshaller marshaller) {
        SMSServiceImpl client = new SMSServiceImpl();
        client.setDefaultUri("https://smsc.kz/sys/soap.php");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);

        PropertyMap<AdFormDto, Ad> adFormDtoToAdMap = new PropertyMap<AdFormDto, Ad>() {
            protected void configure() {
                map().setLocality(new Locality(source.getLocalityId()));
            }
        };
        PropertyMap<Ad, AdFormDto> adToAdFormDtoMap = new PropertyMap<Ad, AdFormDto>() {
            protected void configure() {
                map().setLocalityId(source.getLocality().getId());
                map().setCountryId(source.getLocality().getCountry().getId());
            }
        };
        Converter<Long, Locality> toLocality = new Converter<Long, Locality>() {
            public Locality convert(MappingContext<Long, Locality> context) {
                return context.getSource() == null ? null : new Locality(context.getSource());
            }
        };
        mapper.addConverter(toLocality);
        mapper.addMappings(adFormDtoToAdMap);
        mapper.addMappings(adToAdFormDtoMap);

        mapper.addMappings(getImageToImageDtoMapping());
        return mapper;
    }

    private PropertyMap<Image, ImageDto> getImageToImageDtoMapping(){
        Converter<String, String> extraSmallImageUrlConverter = context -> ResourceHandlerConfig.EXTRA_SMALL_IMAGES_URL + context.getSource();
        Converter<String, String> smallImageUrlConverter = context -> ResourceHandlerConfig.SMALL_IMAGES_URL + context.getSource();
        Converter<String, String> mediumImageUrlConverter = context -> ResourceHandlerConfig.MEDIUM_IMAGES_URL + context.getSource();
        Converter<String, String> bigImageUrlConverter = context -> ResourceHandlerConfig.BIG_IMAGES_URL + context.getSource();
        Converter<String, String> fullImageUrlConverter = context -> ResourceHandlerConfig.FULL_IMAGES_URL + context.getSource();

        return new PropertyMap<Image, ImageDto>() {
            protected void configure() {
                using(extraSmallImageUrlConverter).map(source.getFileNameWithExt()).setUrlForExtraSmallSize(null);
                using(smallImageUrlConverter).map(source.getFileNameWithExt()).setUrlForSmallSize(null);
                using(mediumImageUrlConverter).map(source.getFileNameWithExt()).setUrlForMediumSize(null);
                using(bigImageUrlConverter).map(source.getFileNameWithExt()).setUrlForBigSize(null);
                using(fullImageUrlConverter).map(source.getFileNameWithExt()).setUrlForFullSize(null);
            }
        };
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new HousingTypeConverter());
    }
}
