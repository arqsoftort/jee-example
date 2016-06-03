# jee-example

This is JEE project demostrating some basic usages of EJB, JPA, JMS and REST. It is **NOT** production-ready and it is **NOT** an acceptable quality project. This means, there are lots of unhandled error situations, it does not respect every codification and naming conventions, it does not comply with high REST-API standards, and lots of etceteras.

## Installation

Before importing the project into NetBeans, you should start your Glassfish server and:

#### Create a JDBC Connection Pool

```
create-jdbc-connection-pool --datasourceclassname org.apache.derby.jdbc.ClientDataSource --restype javax.sql.DataSource --property portNumber=1527:password=jeeexample:user=jeeexample:serverName=localhost:databaseName=jeeexample:connectionAttributes=\;create\\=true jee_example_pool
```

#### Create a JDBC Resource

```
create-jdbc-resource --connectionpoolid jee_example_pool jdbc/jee-example-pool
```

#### Create a JMS Connection Factory

```
create-jms-resource --restype javax.jms.ConnectionFactory jms/ConnectionFactory
```

#### Create a JMS Queue

```
create-jms-resource --restype javax.jms.Queue --property Name=Queue jms/Queue
```

## Running

After creating the initial resources, you can deploy the application and test the RESTful webservice at `http://localhost:8080/jee-example-war`.

These are some of the available operations:

```
GET /authors
GET /authors/{id}
GET /authors/{id}/books
GET /authors/{id}/books/{id}
POST /authors
POST /authors/{id}/books
```