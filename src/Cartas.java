public class Cartas {
    private String color;
    private String imgDir; //aqui vai o diretorio da imagem da carta

    public String getImgDir() {
        return imgDir;
    }

    public void setImgDir(String imgDir) {
        this.imgDir = imgDir;
    }

    public void setColor(String color) {
        this.color = color;
    }
    private String value;
    



    public Cartas(String color, String value, String imgDir){
        this.color = color;
        this.value = value;
        this.imgDir = imgDir;
    }

    public String toString() {
        return color + " " + value + " (" + imgDir + ")";
    }
    public String getValue() {
        return value;
    }
    public String getColor() {
        return color;
    }
}
