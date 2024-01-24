package paf.batch3.paf.assesment.batch3pafassesment.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Listing {
    String name;
    Double price;
    String image;
    String id;
    String description;
    String street;
    String suburb;
    String country;
    List<String> amenities;

    public Listing(String name, Double price, String image, String id) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.id = id;
    }

}