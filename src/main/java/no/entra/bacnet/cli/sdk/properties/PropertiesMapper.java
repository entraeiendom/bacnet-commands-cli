package no.entra.bacnet.cli.sdk.properties;

import no.entra.bacnet.cli.sdk.ConfigurationRequest;
import no.entra.bacnet.objects.Property;

import java.util.ArrayList;
import java.util.List;

public class PropertiesMapper {

    public static List<Property> mapProperties(ConfigurationRequest configurationRequest) {
        List<Property> properties = new ArrayList<>();
        configurationRequest.getProperties().entrySet().stream().forEach(crp ->  {
            properties.add(new Property(crp.getKey(), crp.getValue()));
        });

        return properties;
    }
}
