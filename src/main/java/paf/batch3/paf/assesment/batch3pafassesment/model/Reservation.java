package paf.batch3.paf.assesment.batch3pafassesment.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    private String resv_id;
    private String acc_id;

    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Starting date of work must be after than today")
    private Date arrival;

    @NotNull(message = "duration of stay cannot be null")
    @NotEmpty(message = "duration of stay cannot be empty")
    private Integer daysOfStay;

}
