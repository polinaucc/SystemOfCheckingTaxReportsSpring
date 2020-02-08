package ua.polina.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DateDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
