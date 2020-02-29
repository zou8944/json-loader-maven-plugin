
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.zou8944/json-loader-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.zou8944/json-loader-maven-plugin)

# json-loader-maven-plugin
将Json文件加载到Maven工程的properties

参考[properties-maven-plugin](https://github.com/mojohaus/properties-maven-plugin)

Load json file to maven project

# Usage

```xml
<project>
  ...
  <build>
    <!-- To use the plugin goals in your POM or parent POM -->
    <plugins>
      <plugin>
        <groupId>com.github.zou8944</groupId>
        <artifactId>json-loader-maven-plugin</artifactId>
        <version>1.0.0</version>
        <executions>
          <execution>
            <phase>initialize</phase>
            <goals>
              <goal>read-project-json-to-properties</goal>
            </goals>
            <configuration>
              <files>
                <file><!--your file location--></file>
              </files>
            </configuration>
          </execution>
        </executions>
      </plugin>
      ...
    </plugins>
  </build>
  ...
</project>
```

# Example

json-loader-maven-plugin loads the following json file and properties-maven-plugin loads the following properties file with the same result 
```json
{
    "video": {
        "db": {
            "ergedd": {
                "host": "111.111.111.111",
                "port": 1234,
                "username": "username",
                "password": "password",
                "database": "database"
            }
        }
    }
}
```

```properties
video.db.ergedd.host=111.111.111.111
video.db.ergedd.port=1234
video.db.ergedd.username=username
video.db.ergedd.password=password
video.db.ergedd.database=database
```

# Related

- [properties-maven-plugin](https://github.com/mojohaus/properties-maven-plugin)
- [fastjson](https://github.com/alibaba/fastjson)
- [Maven Plugin Developer Centre](https://maven.apache.org/plugin-developers/index.html)