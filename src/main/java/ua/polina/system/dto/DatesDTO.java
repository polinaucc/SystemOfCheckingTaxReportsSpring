package ua.polina.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DatesDTO {
    //TODO maybe remove
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date1;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date2;
}
