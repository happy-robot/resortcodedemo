package kz.kaps.resort.core.constants;

public enum ImageSize {

    EXTRA_SMALL(95, 65),
    SMALL(160, 120),
    MEDIUM(400, 300),
    BIG(600, 450),
    FULL(1200, 900),
    ORIGINAL;

    private int width;
    private int height;

    ImageSize(){}

    ImageSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
