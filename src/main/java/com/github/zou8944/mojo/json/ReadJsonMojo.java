package com.github.zou8944.mojo.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 */
@Mojo(name = "read-project-json-to-properties", defaultPhase = LifecyclePhase.INITIALIZE)
public class ReadJsonMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project}", readonly = true, required = true)
  private MavenProject project;

  @Parameter
  private File[] files;

  @Parameter
  private String[] urls = new String[0];

  /**
   * for test
   *
   * @param project
   */
  public void setProject(MavenProject project) {
    this.project = project;
  }

  /**
   * for test
   *
   * @param files
   */
  public void setFiles(File[] files) {
    if (files == null) {
      this.files = new File[0];
    } else {
      this.files = new File[files.length];
      System.arraycopy(files, 0, this.files, 0, files.length);
    }
  }

  /**
   * for test
   *
   * @param urls
   */
  public void setUrls(String[] urls) {
    if (urls == null) {
      this.urls = null;
    } else {
      this.urls = new String[urls.length];
      System.arraycopy(urls, 0, this.urls, 0, urls.length);
    }
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {

    loadFiles();

    loadUrls();

  }

  private void loadFiles() throws MojoExecutionException {
    for (File file : files) {
      load(new FileResource((file)));
    }
  }

  private void loadUrls() throws MojoExecutionException {
    for (String url : urls) {
      load(new UrlResource((url)));
    }
  }

  private void load(Resource resource) throws MojoExecutionException {
    if (resource.canBeOpened()) {
      loadJson(resource);
    } else {
      throw new MojoExecutionException("Json file could not be loaded from " + resource);
    }
  }

  private void loadJson(Resource resource) throws MojoExecutionException {
    try {

      getLog().info("Loading json from " + resource);

      try (InputStream stream = resource.getInputStream()) {
        Map result = JSON.parseObject(stream, Map.class, Feature.values());
        Properties properties = parseMap2Properties(result);
        project.getProperties().putAll(properties);
      }

      getLog().info("Loading json complete !!!");

    } catch (IOException e) {
      throw new MojoExecutionException("Error reading json from " + resource, e);
    }
  }

  /**
   * Convert map to properties
   *
   * @param map
   * @return
   */
  private Properties parseMap2Properties(Map<String, Object> map) {
    Properties properties = new Properties();
    Map<String, Object> temp = new HashMap<>(map);

    while (temp.size() > 0) {

      String key = temp.keySet().iterator().next();
      Object value = temp.get(key);
      temp.remove(key);

      if (value instanceof Map) {
        ((Map) value).forEach((key1, value1) -> temp.put(key + "." + key1, value1));
      } else {
        properties.put(key, value.toString());
      }
    }

    return properties;
  }

}