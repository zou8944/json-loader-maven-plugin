package com.github.zou8944.mojo.json;

import com.alibaba.fastjson.JSONObject;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class ReadPropertiesMojoTest {

  private MavenProject projectStub;
  private ReadJsonMojo readJsonMojo;

  @Before
  public void setUp() {
    projectStub = new MavenProject();
    readJsonMojo = new ReadJsonMojo();
    readJsonMojo.setProject(projectStub);
  }

  @Test
  public void readJsonFile() throws Exception {
    File testFile = constructTestFile();
    Properties baseProperties = constructComparedProperties();

    readJsonMojo.setFiles(new File[]{testFile});
    readJsonMojo.execute();

    Properties projectProperties = projectStub.getProperties();

    assertNotNull(projectProperties);
    assertNotEquals(0, projectProperties.size());
    assertEquals(baseProperties.size(), projectProperties.size());
    assertEquals(baseProperties, projectProperties);
  }

  private File constructTestFile() throws IOException {
    // File to be tested
    File file = File.createTempFile("read-json", ".json");
    file.deleteOnExit();
    try (FileWriter writer = new FileWriter(file)) {
      String str = new JSONObject().fluentPut("test", new JSONObject()
        .fluentPut("property1", "value1")
        .fluentPut("property2", "2")).toJSONString();
      writer.write(str);
      writer.flush();
    }
    return file;
  }

  private Properties constructComparedProperties() {
    Properties properties = new Properties();
    properties.put("test.property1", "value1");
    properties.put("test.property2", "2");
    return properties;
  }

}
