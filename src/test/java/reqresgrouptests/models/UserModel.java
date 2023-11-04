package reqresgrouptests.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserModel {

    String page;
    String per_page;
    String total;
    String total_pages;
    @JsonProperty("data")
    List<DataList> dataList;
    Support support;

}

