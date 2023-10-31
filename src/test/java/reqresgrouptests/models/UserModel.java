package reqresgrouptests.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import java.util.List;

@Data
public class UserModel {

    String page;
    String per_page;
    String total;
    String total_pages;
   @JsonAlias("data")
    List<DataList> dataList;
    Support support;

}

