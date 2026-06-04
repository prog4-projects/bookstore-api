package prog.hei.app.conf;

import org.springframework.test.context.DynamicPropertyRegistry;
import prog.hei.app.PojaGenerated;

@PojaGenerated
public class EmailConf {

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("aws.ses.source", () -> "dummy-ses-source");
  }
}
