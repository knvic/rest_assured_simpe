package reqresgrouptests.tests;

import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import reqresgrouptests.config.ApiConfig;

public class BaseTest {

    protected static final ApiConfig config = ConfigFactory.create(ApiConfig.class, System.getProperties());
}
