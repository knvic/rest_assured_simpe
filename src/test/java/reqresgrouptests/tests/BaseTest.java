package reqresgrouptests.tests;

import org.aeonbits.owner.ConfigFactory;
import reqresgrouptests.config.ApiConfig;

public class BaseTest {

    protected static final ApiConfig config = ConfigFactory.create(ApiConfig.class, System.getProperties());
}
