# ELK-Stack-Tutorial
In Following you will get a brief guide on how to quickly setup a Log Management Solution with the ELK Stack (Elasticsearch-Logstash-Kibana)

When time comes to deploy a new project, one often overlooked aspect is logging and log management. ELK stack developed by [Elastic](https://www.elastic.co), is a powerful and open source log management solution. ELK stands for Elasticsearch, Logstash and Kibana:
+ [Elasticsearch](https://www.elastic.co/products/elasticsearch) is a RESTful search engine, based on [Lecene](https://lucene.apache.org/) and as the heart of Elastic Stack, is responsible for performing and combining many types of searches amongs logs. 
+ [Kibana](https://www.elastic.co/products/kibana) is an open source plugin that visualizes Elasticseach data.
+ [Logstash](https://www.elastic.co/products/logstash) is a server-side data processing pipeline that reads data from a source, transform it, and sends it to a "stash" (In this tutorial, the stash is Elasticseach).

### Installing Elasticsearch
  1. Download Elasticsearch compressed file from [here](https://www.elastic.co/downloads/elasticsearch) and unzip it
  2. run it with ```bin/elasticsearch``` or ```bin/elasticsearch.bat``` on Windows
