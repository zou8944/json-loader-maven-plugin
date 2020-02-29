package com.github.zou8944.mojo.json;

import org.apache.maven.plugin.MojoExecutionException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

abstract class Resource {

  private InputStream stream;

  public abstract boolean canBeOpened();

  protected abstract InputStream openStream() throws IOException;

  public InputStream getInputStream() throws IOException {

    if (stream == null) {
      stream = openStream();
    }

    return stream;
  }
}

class FileResource extends Resource {

  private final File file;

  public FileResource(File file) {
    this.file = file;
  }

  @Override
  public boolean canBeOpened() {
    return file.exists();
  }

  @Override
  protected InputStream openStream() throws IOException {
    return new BufferedInputStream(new FileInputStream(file));
  }

  public String toString() {
    return "File: " + file;
  }
}

class UrlResource extends Resource {

  private static final String CLASSPATH_PREFIX = "classpath:";

  private static final String SLASH_PREFIX = "/";

  private final URL url;

  private boolean isMissingClasspathResouce = false;

  private String classpathUrl;

  public UrlResource(String url) throws MojoExecutionException {
    if (url.startsWith(CLASSPATH_PREFIX)) {
      String resource = url.substring(CLASSPATH_PREFIX.length());
      if (resource.startsWith(SLASH_PREFIX)) {
        resource = resource.substring(1);
      }
      this.url = getClass().getClassLoader().getResource(resource);
      if (this.url == null) {
        isMissingClasspathResouce = true;
        classpathUrl = url;
      }
    } else {
      try {
        this.url = new URL(url);
      } catch (MalformedURLException e) {
        throw new MojoExecutionException("Badly formed URL " + url + " - " + e.getMessage());
      }
    }
  }

  public boolean canBeOpened() {
    if (isMissingClasspathResouce) {
      return false;
    }
    try {
      openStream();
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  protected InputStream openStream() throws IOException {
    return new BufferedInputStream(url.openStream());
  }

  public String toString() {
    if (!isMissingClasspathResouce) {
      return "URL " + url.toString();
    }
    return classpathUrl;
  }
}