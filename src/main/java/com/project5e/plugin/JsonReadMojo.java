package com.project5e.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Properties;

@Mojo(name = "json-file-loader", defaultPhase = LifecyclePhase.INITIALIZE)
public class JsonReadMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project.properties}", required = true, readonly = true)
  Properties properties;

  @Parameter(property = "feil")
  String filePath;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {



  }

}
