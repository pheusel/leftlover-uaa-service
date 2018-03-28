package dhbw.leftlovers.service.uaa.register;


import lombok.Getter;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ServiceConfig implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    private String serviceName = "UAAService", serviceAddress = "https://uaaservice.herokuapp.com";
    private int servicePort = 443;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        ServiceRegistration serviceRegistration = new ServiceRegistration(this.serviceName, this.serviceAddress, this.servicePort);

    }
}
