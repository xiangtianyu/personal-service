package ty.xiang.xty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ty.xiang.xty.util.Constrain;

@SpringBootApplication
@EnableConfigurationProperties(Constrain.class)
public class XtyApplication {

	public static void main(String[] args) {
		SpringApplication.run(XtyApplication.class, args);
	}
}
