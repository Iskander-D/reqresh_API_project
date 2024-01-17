package lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDataList {

    @JsonProperty("total_pages")
    private Integer totalPages;
}
