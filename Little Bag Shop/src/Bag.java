import java.io.Serializable;

public class Bag implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }

    public double getPrice() {
        return price;
    }

    private String brand;
    private String category;
    private String size;
    private String colour;
    private double price;

    public Bag(String id, String brand, String category, String size, String colour, double price) {
        this.id = id;
        this.brand = brand;
        this.category = category;
        this.size =  size ;
        this.colour = colour;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Brand: " + brand + ", Category: " + category + "size: "+size + ", Colour: "+colour + ", Price: $" + price;
    }
}
