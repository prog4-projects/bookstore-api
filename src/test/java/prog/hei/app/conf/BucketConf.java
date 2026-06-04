package prog.hei.app.conf;

import org.springframework.test.context.DynamicPropertyRegistry;
import prog.hei.app.PojaGenerated;

@PojaGenerated
public class BucketConf {

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("aws.s3.bucket", () -> "dummy-bucket");
  }
}
