# ELK-Stack-Tutorial

When time comes to deploy a new project, one often overlooked aspect is logging and log management. ELK stack developed by [Elastic](https://www.elastic.co), is a powerful and open source log management solution. ELK stands for Elasticsearch, Logstash and Kibana:
+ [Elasticsearch](https://www.elastic.co/products/elasticsearch) is a RESTful search engine, based on [Lecene](https://lucene.apache.org/) and as the heart of Elastic Stack, is responsible for performing and combining many types of searches amongs logs. 
+ [Kibana](https://www.elastic.co/products/kibana) is an open source plugin that visualizes Elasticseach data.
+ [Logstash](https://www.elastic.co/products/logstash) is a server-side data processing pipeline that reads data from a source, transform it, and sends it to a "stash" (In this tutorial, the stash is Elasticseach).

In Following you will get a brief guide on how to quickly setup a Log Management Solution with the ELK Stack alongside Spring Boot using TCP connection.

### 1. Install Elasticsearch
1. Download Elasticsearch compressed file from [here](https://www.elastic.co/downloads/elasticsearch) and unzip it
2. Run it with ```bin/elasticsearch``` or ```bin/elasticsearch.bat``` on Windows
3. Check it using ```curl -XGET http://localhost:9200```. You should get the following result:

```{
  "name" : "mN9s_5E",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "alapTemERFu7FQGdIc-PIA",
  "version" : {
    "number" : "6.2.2",
    "build_hash" : "10b1edd",
    "build_date" : "2018-02-16T19:01:30.685723Z",
    "build_snapshot" : false,
    "lucene_version" : "7.2.1",
    "minimum_wire_compatibility_version" : "5.6.0",
    "minimum_index_compatibility_version" : "5.0.0"
  },
  "tagline" : "You Know, for Search"
}
```

### 2. Install Kibana
1. Download Kibana from [here](https://www.elastic.co/downloads/kibana) and extract it
2. Run it using ```bin/kibana```
3. Open [127.0.0.1:5601](http://127.0.0.1:5601) in your browser and check if Kibana page shows up
 
### 3. Install Logstash
[Download Logstash](https://www.elastic.co/downloads/logstash) and unzip it
 
 
### 4. Creating Logstash config file 
1. Create a config file using ```touch logstash.conf```
2. Append a simple configuration to your config file with command:
 ```echo "input { stdin { } } output { stdout{ } elasticsearch { hosts => '127.0.0.1' } }" >> logstash.conf```
    
    As you can see the configuration file consist of two blocks:
 + Input block defines from where Logstash should read input data. In our case it it will be ```stdin{}``` (Standart input stream)
 + Output section contains output plugins that send data from input to particular destinations.
 
### 5. Running Logstash  
 1. Run ```{Path-to-Logstash}/bin/logstash -f logstash.conf``` and wait for terminal user input
 2. Enter ```HELLO WORLD!``` in terminal
 3. Point your browser to [127.0.0.1:5601](http://127.0.0.1:5601)
 4. Click on Set up index patterns
 5. Enter ```*``` as index name and click Next Step
 6. Choose @timestamp from time filter drop down and click on Create index pattern
 7. Done... You will see Hello World in your logs
 
 ### 6. Configuring Spring Boot
 1. Add the following dependencies in your ```pom.xml```
 ```xml
 <dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
	<scope>test</scope>
</dependency>

<dependency>
	<groupId>net.logstash.logback</groupId>
	<artifactId>logstash-logback-encoder</artifactId>
	<version>4.11</version>
</dependency>
```

2. In your Spring Boot project, create ```logback-spring.xml``` in the root of your classpath. This file usually includes main logging configuration such as log levels, destination, log pattern, as shown in the following example:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/base.xml" />
    <appender name="stash"
              class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>127.0.0.1:5000</destination>
        <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <pattern>
                    <pattern>
                        {
                        "service": "logtester",
                        "host": "%property{hostname}",
                        "ts": "%date{yyyy-MM-dd'T'HH:mm:ss.SSSZ}",
                        "level": "%level",
                        "message": "%msg %ex{short}",
                        "rid": "%mdc{rid}",
                        "vid":
                        "%mdc{vid}",
                        "cid": "%mdc{cid}",
                        "land": "%mdc{land}",
                        "logger" :
                        "%logger"
                        }
                    </pattern>
                </pattern>
                <stackTrace>
                    <throwableConverter
                            class="net.logstash.logback.stacktrace.ShortenedThrowableConverter">
                        <maxDepthPerThrowable>90</maxDepthPerThrowable>
                        <exclude>sun\.reflect\..*\.invoke.*</exclude>
                        <exclude>net\.sf\.cglib\.proxy\.MethodProxy\.invoke</exclude>
                        <rootCauseFirst>true</rootCauseFirst>
                    </throwableConverter>
                </stackTrace>
            </providers>
        </encoder>
    </appender>
    <appender name="SAVE-TO-FILE" class="ch.qos.logback.core.FileAppender">
        <file>${LOG_PATH}/logtester.log</file>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <Pattern>
                %d{dd-MM-yyyy HH:mm:ss.SSS} [%thread] %-5level
                %logger{36}.%M - %msg%n
            </Pattern>
        </encoder>
    </appender>
    <root level="error">
        <appender-ref ref="SAVE-TO-FILE" />
        <appender-ref ref="stash" />
    </root>
    <root level="warn">
        <appender-ref ref="SAVE-TO-FILE" />
        <appender-ref ref="stash" />
    </root>
    <root level="info">
        <appender-ref ref="SAVE-TO-FILE" />
        <appender-ref ref="stash" />
    </root>
</configuration>
```
As you can see we send all logs to the tcp port ```127.0.0.1:5000```
3. Add following line to ```application.properties```
```
logging.path=logs
```
### 7. Configuring Logstash again to listen to your Spring Boot logger
Now it's time to change the logstash config file again. This time our input will be a tcp port, which should read all logs from the Spring Boot logger. Additionally, we add a filter to have nicer and queryable structure:
```
input {
  tcp {
    port => 5000
    type => syslog
    host => "127.0.0.1"
  }
}

filter {
  if [type] == "syslog" {
    grok {
      match => { "message" => "%{SYSLOGTIMESTAMP:syslog_timestamp} %{SYSLOGHOST:syslog_hostname} %{DATA:syslog_program}(?:\[%{POSINT:syslog_pid}\])?: %{GREEDYDATA:syslog_message}" }
      add_field => [ "received_at", "%{@timestamp}" ]
      add_field => [ "received_from", "%{host}" ]
    }
    date {
      match => [ "syslog_timestamp", "MMM  d HH:mm:ss", "MMM dd HH:mm:ss" ]
    }
  }
}

output {
  elasticsearch { hosts => ["localhost:9200"] }
  stdout { codec => rubydebug }
}
```
Run logstash again using the command ```/Path/To/Logstash/bin/logstash -f /Path/To/Logstash/Config.conf```
And Finally run your Spring Boot Project

#### Congratulation! You will see now all your logs in KIBANA
