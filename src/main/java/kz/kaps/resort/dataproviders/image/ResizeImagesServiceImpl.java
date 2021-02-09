package kz.kaps.resort.dataproviders.image;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.kaps.resort.core.usecase.image.resizing.ResizeImagesService;
import kz.kaps.resort.core.usecase.image.resizing.ResizeCommandDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class ResizeImagesServiceImpl implements ResizeImagesService {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.original-image-resize-producer.topic}")
    private String resizeOriginalImageTopic;

    public ResizeImagesServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void resizeAndSaveImageFiles(ResizeCommandDto resizeCommandDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String resizeCommandJson = objectMapper.writeValueAsString(resizeCommandDto);
        kafkaTemplate.send(resizeOriginalImageTopic, resizeCommandJson);
    }
}
