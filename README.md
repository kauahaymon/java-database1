# MySQL Java Connector Project

This project demonstrates how to set up and use the MySQL Java Connector to connect to a MySQL database using Java.

## Step 1: Setting up the MySQL Java Connector

1. **Create a Database:**

    In MySQL Workbench, create a database named `coursejdbc`:

    ```sql
    CREATE DATABASE coursejdbc;
    ```

2. **Download the MySQL Java Connector:**

    - Download the MySQL Java Connector from the [MySQL website](https://dev.mysql.com/downloads/connector/j/).
    - Select the "Platform Independent" option and download the `.zip` file.

3. **Create a Directory for Java Libraries:**

    Open the terminal and create the `java-libs` directory:

    ```bash
    sudo mkdir -p /usr/share/java/java-libs
    ```

4. **Copy the MySQL Connector JAR file:**

    - Navigate to the directory where you downloaded the MySQL Connector:

      ```bash
      cd ~/Downloads/mysql-connector-j-8.4.0/
      ```

    - Copy the JAR file to the `java-libs` directory:

      ```bash
      sudo cp -p mysql-connector-j-8.4.0.jar /usr/share/java/java-libs
      ```

5. **Set Up in IntelliJ:**

    - Open IntelliJ and create a new project.
    - Configure the MySQL Connector library:
        - Go to `File > Project Structure > Libraries`.
        - Click the `+` icon to add a new library.
        - Name the library `MySQLConnector`.
        - Add the JAR file from `/usr/share/java/java-libs/mysql-connector-j-8.4.0.jar`.

## Step 2: Setting up the Database Connection

1. **Create the `db.properties` File:**

    In the root directory of the `src` folder, create a new file named `db.properties` and add the following content:

    ```properties
    user=developer
    password=1234567
    dburl=jdbc:mysql://localhost:3306/coursejdbc
    useSSL=false
    ```

2. **Create the `db` Package and Exception Class:**

    - Create a package named `db`.
    - Inside the `db` package, create a class named `DbException` with the following code:

      ```java
      package db;

      public class DbException extends RuntimeException {
          public DbException(String msg) {
              super(msg);
          }
      }
      ```

3. **Create the `DB` Class:**

    - In the `db` package, create a class named `DB` and add the following methods:

### Method `getConnection`:

This method establishes a connection with the database.

```java
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }
}
```
Explanation:

- Checks if the connection (`conn`) is null.
- Loads the database properties using the `loadProperties()` method.
- Gets the database URL from the properties.
- Establishes the connection with the database using `DriverManager.getConnection`.
- If a `SQLException` occurs, it throws a `DbException` with the error message.

## Method `closeConnection`:

This method closes the connection with the database.

```java
public static void closeConnection() {
    if (conn != null) {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}
```
Explanation:

- Checks if the connection (`conn`) is not null.
- Closes the connection.
- If a `SQLException` occurs, it throws a `DbException` with the error message.

## Method `loadProperties`:

This method loads the properties from the `db.properties` file.

```java
private static Properties loadProperties() {
    try (FileInputStream fileInput = new FileInputStream("db.properties")) {
        Properties properties = new Properties();
        properties.load(fileInput);
        return properties;
    } catch (IOException e) {
        throw new DbException(e.getMessage());
    }
}
```
Explanation:

- Opens the `db.properties` file using a `FileInputStream`.
- Loads the properties from the file.
- Returns the loaded properties.
- If an `IOException` occurs, it throws a `DbException` with the error message.

4. **Overview of the `DB` Class:**

    - The `DB` class contains methods to connect to and disconnect from the database, as well as to load database properties from the `db.properties` file.
    - It uses the `DbException` class to handle any exceptions that occur during database operations.

## Complete `DB` Class Code:

```java
package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fileInput = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fileInput);
            return properties;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
}
``
