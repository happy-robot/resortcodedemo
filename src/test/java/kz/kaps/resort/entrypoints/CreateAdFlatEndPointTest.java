package kz.kaps.resort.entrypoints;

import kz.kaps.resort.entrypoints.html.landlord.dto.AdFormDto;
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
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {CreateAdFlatEndPointTest.Initializer.class})
public class CreateAdFlatEndPointTest {

    private static final Long AD_FLAT_ID = 100L;

    private static final String CREATE_AD_FLAT_URL = "/my/ads/create/flat";
    private static final String IMAGE_UPLOAD_URL
            = "/api/rest/v1/my/ads/" + AD_FLAT_ID + "/images";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

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
        mockMvc.perform(get(CREATE_AD_FLAT_URL)).andExpect(status().isOk());

        MockMultipartFile firstFile = new MockMultipartFile(
                "images[]", "filename.jpg", "image/jpeg", "some xml".getBytes());
        mockMvc.perform(
                MockMvcRequestBuilders.multipart(IMAGE_UPLOAD_URL)
                .file(firstFile)).andExpect(status().isOk());

        mockMvc.perform(post(CREATE_AD_FLAT_URL)
                .contentType(APPLICATION_FORM_URLENCODED)
                .content(getFilledForm())
                .param("sendWelcomeMail", "true"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/my/ads/edit/flat/" + AD_FLAT_ID));
    }

    private String getFilledForm() throws EncoderException {
        URLCodec codec = new URLCodec();
        String form =
                codec.encode(AdFormDto.ATTRIBUTE_HEADER) + "=" + "testheader"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_ID) + "=" + AD_FLAT_ID
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_ROOM_NUM) + "=" + "3"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_FLOOR) + "=" + "4"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_BUILDING_FLOORS) + "=" + "5"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_BUILDING_FLOORS) + "=" + "5"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_SLEEP_NUM) + "=" + "6"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_AREA) + "=" + "70"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_DESCRIPTION) + "=" + "Best flat!"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_HOT_WATER) + "=" + "true"
                        + '&' + "_" + codec.encode(AdFormDto.ATTRIBUTE_HOT_WATER) + "=" + "on"

                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_COLD_WATER) + "=" + "true"
                        + '&' + "_" + codec.encode(AdFormDto.ATTRIBUTE_COLD_WATER) + "=" + "on"

                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_TV) + "=" + "true"
                        + '&' + "_" + codec.encode(AdFormDto.ATTRIBUTE_TV) + "=" + "on"

                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_FRIDGE) + "=" + "true"
                        + '&' + "_" + codec.encode(AdFormDto.ATTRIBUTE_FRIDGE) + "=" + "on"

                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_STOVE) + "=" + "true"
                        + '&' + "_" + codec.encode(AdFormDto.ATTRIBUTE_STOVE) + "=" + "on"

                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_WASHER) + "=" + "true"
                        + '&' + "_" + codec.encode(AdFormDto.ATTRIBUTE_WASHER) + "=" + "on"

                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_MICROWAVE) + "=" + "true"
                        + '&' + "_" + codec.encode(AdFormDto.ATTRIBUTE_MICROWAVE) + "=" + "on"

                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_WIFI) + "=" + "true"
                        + '&' + "_" + codec.encode(AdFormDto.ATTRIBUTE_WIFI) + "=" + "on"

                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_CONDITIONER) + "=" + "true"
                        + '&' + "_" + codec.encode(AdFormDto.ATTRIBUTE_CONDITIONER) + "=" + "on"

                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_BRAZIER) + "=" + "true"
                        + '&' + "_" + codec.encode(AdFormDto.ATTRIBUTE_BRAZIER) + "=" + "on"

                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_COUNTRY_ID) + "=" + "1"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_LOCALITY_ID) + "=" + "2"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_STREET) + "=" + "Turan"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_HOUSE_NUMBER) + "=" + "48"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_WEEKDAY_PRICE_PER_DAY) + "=" + "18000"
                        + '&' + codec.encode(AdFormDto.ATTRIBUTE_HOLYDAY_PRICE_PER_DAY) + "=" + "22000";
        return form;
    }

}
