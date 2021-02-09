package kz.kaps.resort.core.usecase.image.resizing;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResizeCommandDto {

    private String originalImgPath;
    private List<TargetImage> targetImages;

    public void addTargetImage(TargetImage targetImage) {
        if(targetImages == null) targetImages = new ArrayList<>();
        targetImages.add(targetImage);
    }

    @Data
    public static class TargetImage {
        private String targetImagePath;
        private int heightInPixel;
        private int widthInPixel;
    }
}
