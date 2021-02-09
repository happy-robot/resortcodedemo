package kz.kaps.resort.entrypoints;

import kz.kaps.resort.entrypoints.html.landlord.dto.AdCottageFormDto;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {CreateAdCottageEndPointTest.Initializer.class})
public class CreateAdCottageEndPointTest {

    private static final Long AD_ID = 100L;

    private static final String CREATE_AD_URL = "/my/ads/create/cottage";
    private static final String IMAGE_UPLOAD_URL
            = "/api/rest/v1/my/ads/" + AD_ID + "/images";

    @Autowired
    private MockMvc mockMvc;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }


    @Test
    @Sql({"/schema.sql", "/test-data.sql"})
    @WithMockUser(username = "87013904442")
    public void createAdProcessSuccess() throws Exception {
        mockMvc.perform(get(CREATE_AD_URL)).andExpect(status().isOk());

        MockMultipartFile firstFile = new MockMultipartFile(
                "images[]", "filename.jpg", "image/jpeg", "some xml".getBytes());
        mockMvc.perform(
                MockMvcRequestBuilders.multipart(IMAGE_UPLOAD_URL)
                        .file(firstFile)).andExpect(status().isOk());

        mockMvc.perform(post(CREATE_AD_URL)
                .contentType(APPLICATION_FORM_URLENCODED)
                .content(getFilledForm())
                .param("sendWelcomeMail", "true"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/my/ads/edit/cottage/" + AD_ID));
    }

    private String getFilledForm() throws EncoderException {
        URLCodec codec = new URLCodec();
        String form =
                codec.encode(AdCottageFormDto.ATTRIBUTE_HEADER) + "=" + "testheader"
                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_ID) + "=" + AD_ID
                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_ROOM_NUM) + "=" + "7"
                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_FLOOR_NUM) + "=" + "2"
                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_SLEEP_NUM) + "=" + "6"
                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_AREA) + "=" + "170"
                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_DESCRIPTION) + "=" + "Best flat!"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_HOT_WATER) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_HOT_WATER) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_COLD_WATER) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_COLD_WATER) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_TV) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_TV) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_FRIDGE) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_FRIDGE) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_STOVE) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_STOVE) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_WASHER) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_WASHER) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_MICROWAVE) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_MICROWAVE) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_WIFI) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_WIFI) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_CONDITIONER) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_CONDITIONER) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_BRAZIER) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_BRAZIER) + "=" + "on"


                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_SAUNA) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_SAUNA) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_BATHHOUSE) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_BATHHOUSE) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_SWIMMING_POOL) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_SWIMMING_POOL) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_SUMMER_KITCHEN) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_SUMMER_KITCHEN) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_COOK) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_COOK) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_ALCOVE) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_ALCOVE) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_TERRITORY_AREA) + "=" + "10"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_KAZAN) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_KAZAN) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_TAPCHAN) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_TAPCHAN) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_BICYCLES) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_BICYCLES) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_QUAD_BIKES) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_QUAD_BIKES) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_PLAY_GROUND) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_PLAY_GROUND) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_BILLIARDS) + "=" + "true"
                        + '&' + "_" + codec.encode(AdCottageFormDto.ATTRIBUTE_BILLIARDS) + "=" + "on"

                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_COUNTRY_ID) + "=" + "1"
                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_LOCALITY_ID) + "=" + "2"
                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_STREET) + "=" + "Turan"
                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_HOUSE_NUMBER) + "=" + "48"
                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_WEEKDAY_PRICE_PER_DAY) + "=" + "18000"
                        + '&' + codec.encode(AdCottageFormDto.ATTRIBUTE_HOLYDAY_PRICE_PER_DAY) + "=" + "22000";
        return form;
    }
}
