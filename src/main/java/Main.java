import com.sun.xml.internal.ws.client.ClientTransportException;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.filter.LoggingFilter;
import unjenkins.dto.Job;
import unjenkins.proxy.IBuild;
import org.glassfish.jersey.jackson.JacksonFeature;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by Kurt on 11/20/15.
 */

public class Main {

    public static void main(String[] stringargs) throws IOException {
        System.setProperty("javax.net.ssl.trustStore", "/repos/javakeys");
        System.setProperty("javax.net.ssl.trustStorePassword", "javakeys");


        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("kurt.waechter@inin.com", "55079fb68d9018d7a57d0633161a415a");
        Client client = ClientBuilder.newClient();
                client.register(feature);
        client.register(JacksonFeature.class);
        client.register(new LoggingFilter());
        WebTarget target = client.target("https://jenkins.inintca.com:8443");

        IBuild jobClient = WebResourceFactory.newResource(IBuild.class, target);
        String url = URLEncoder.encode("displayName[displayName],builds[number,url]", "UTF-8");

        Job job = jobClient.getJob("content-management-service-tests-dev", url);
        System.out.println(job.getDisplayName());

        System.in.read();
    }
}
