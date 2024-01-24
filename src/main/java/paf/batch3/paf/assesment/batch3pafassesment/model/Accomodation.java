package paf.batch3.paf.assesment.batch3pafassesment.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accomodation {

    @NotNull(message = "Country cannot be null")
    @NotEmpty(message = "Country cannot be empty")
    private String country;

    @NotNull(message = "Number of persons cannot be null")
    @Min(value = 1, message = "Number of persons cannot be less than 1")
    @Max(value = 10, message = "Number of persons cannot be more than 10")
    private Integer accomodates;


    @NotNull(message = "Range minimum cannot be null")
    @Min(value = 1, message = "Range minimum cannot be less than 1")
    private Double rangeMin;

    @NotNull(message = "Range maximum cannot be null")
    @Max(value = 1000, message = "Range maximum cannot be more than 10000")
    private Double rangeMax;

    
}
