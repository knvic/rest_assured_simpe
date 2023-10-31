package reqresgrouptests.config;


import org.aeonbits.owner.Config;

@ApiConfig.Sources({
        "classpath:config.properties"
})
public interface ApiConfig extends Config {

    @Key("baseURI")
    String getBaseUrl();

    @Key("basePath")
    String getBasePath();
}