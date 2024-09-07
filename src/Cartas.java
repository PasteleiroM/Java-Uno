public class Cartas {
    private String color;


    private String value;
    



    public Cartas(String color, String value){
        this.color = color;
        this.value = value;
    }

    public String toString() {
        return color + " " + value; //a
    }
    public String getValue() {
        return value;
    }
    public String getColor() {
        return color;
    }
}
