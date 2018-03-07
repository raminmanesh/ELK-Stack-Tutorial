# ELK-Stack-Tutorial

When time comes to deploy a new project, one often overlooked aspect is logging and log management. ELK stack developed by [Elastic](https://www.elastic.co), is a powerful and open source log management solution. ELK stands for Elasticsearch, Logstash and Kibana:
+ [Elasticsearch](https://www.elastic.co/products/elasticsearch) is a RESTful search engine, based on [Lecene](https://lucene.apache.org/) and as the heart of Elastic Stack, is responsible for performing and combining many types of searches amongs logs. 
+ [Kibana](https://www.elastic.co/products/kibana) is an open source plugin that visualizes Elasticseach data.
+ [Logstash](https://www.elastic.co/products/logstash) is a server-side data processing pipeline that reads data from a source, transform it, and sends it to a "stash" (In this tutorial, the stash is Elasticseach).

In Following you will get a brief guide on how to quickly setup a Log Management Solution with the ELK Stack.

### 1. Install Elasticsearch
 1. Download Elasticsearch compressed file from [here](https://www.elastic.co/downloads/elasticsearch) and unzip it
 2. run it with ```bin/elasticsearch``` or ```bin/elasticsearch.bat``` on Windows
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
 3. Open (127.0.0.1:5601) in your browser and check if Kibana page shows up
