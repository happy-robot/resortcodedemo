package kz.kaps.resort.propertiesproviders;

import kz.kaps.resort.core.properties.ImageProperties;

public class ImagePropertiesProvider implements ImageProperties {

    private String folderForExtraSmallImgs;
    private String folderForSmallImgs;
    private String folderForMediumImgs;
    private String folderForBigImgs;
    private String folderForFullImgs;
    private String folderForOriginalImgs;

    @Override
    public String getFolderForExtraSmallImgs() {
        return folderForExtraSmallImgs;
    }

    public void setFolderForExtraSmallImgs(String folderForExtraSmallImgs) {
        this.folderForExtraSmallImgs = folderForExtraSmallImgs;
    }

    @Override
    public String getFolderForSmallImgs() {
        return folderForSmallImgs;
    }

    public void setFolderForSmallImgs(String folderForSmallImgs) {
        this.folderForSmallImgs = folderForSmallImgs;
    }

    @Override
    public String getFolderForMediumImgs() {
        return folderForMediumImgs;
    }

    public void setFolderForMediumImgs(String folderForMediumImgs) {
        this.folderForMediumImgs = folderForMediumImgs;
    }

    @Override
    public String getFolderForBigImgs() {
        return folderForBigImgs;
    }

    public void setFolderForBigImgs(String folderForBigImgs) {
        this.folderForBigImgs = folderForBigImgs;
    }

    @Override
    public String getFolderForFullImgs() {
        return folderForFullImgs;
    }

    public void setFolderForFullImgs(String folderForFullImgs) {
        this.folderForFullImgs = folderForFullImgs;
    }

    @Override
    public String getFolderForOriginalImgs() {
        return folderForOriginalImgs;
    }

    public void setFolderForOriginalImgs(String folderForOriginalImgs) {
        this.folderForOriginalImgs = folderForOriginalImgs;
    }
}
