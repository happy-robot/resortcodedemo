package kz.kaps.resort.core.usecase.image.resizing;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ResizeImagesService {

    void resizeAndSaveImageFiles(ResizeCommandDto resizeCommandDto) throws JsonProcessingException;

}
