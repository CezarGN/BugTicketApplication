package core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public class SpringUnitBaseTest {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void initMocks() {
    MockitoAnnotations.openMocks(this);
  }

  public String toJson(Object obj) throws JsonProcessingException {
    return objectMapper.writeValueAsString(obj);
  }
}