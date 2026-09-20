# Assignment 2 — Setup & Deployment

3-tier Jakarta EE e-business system (JSF + EJB + JPA/MySQL), built on top of the
Week 10 Jakarta web security project. This note covers how to compile, configure
and deploy the application. It is the basis for documentation criterion 1
(compiling & deployment).

## Prerequisites (match these versions)

- NetBeans (with the Jakarta EE / GlassFish plugin)
- GlassFish 7.0.9 **standalone** server (not the NetBeans embedded one)
- MySQL Server 8.x
- JDK 17 or 21
- MySQL Connector/J: `mysql-connector-java-8.0.29.jar`
- FakeSMTP (the "CentreMail" fake mail server): http://nilhcem.com/FakeSMTP/download.html

## 1. MySQL database

Create an (empty) database; the tables are generated automatically on first
deploy (`eclipselink.ddl-generation = create-tables`).

```sql
CREATE DATABASE assignment2db;
```

## 2. GlassFish JDBC connection pool and resource

The persistence unit (`src/conf/persistence.xml`) uses the data source
**`jdbc/JakartaAuthDS`**, so the resource must be created with exactly that name.

1. Copy `mysql-connector-java-8.0.29.jar` into `<glassfish>/glassfish/domains/domain1/lib`.
2. Start the domain: `asadmin start-domain`
3. Create the connection pool (adjust user/password/URL to your MySQL):

```
asadmin create-jdbc-connection-pool \
  --datasourceclassname com.mysql.cj.jdbc.MysqlDataSource \
  --restype javax.sql.DataSource \
  --property user=root:password=YOURPASS:URL="jdbc\:mysql\://localhost\:3306/assignment2db?useSSL=false&serverTimezone=UTC" \
  Assignment2Pool
```

4. Ping the pool to confirm connectivity, then create the JDBC resource:

```
asadmin ping-connection-pool Assignment2Pool
asadmin create-jdbc-resource --connectionpoolid Assignment2Pool jdbc/JakartaAuthDS
```

(These steps can also be done through the GlassFish Admin Console at
http://localhost:4848.)

## 3. FakeSMTP (email verification & recovery)

Registration and account recovery send an email through a fake SMTP server.

1. Run FakeSMTP and set the listening port to **2525** (the port used in
   `AutenticationBean`), then click **Start server**.
2. Verification / recovery codes arrive in the FakeSMTP window; copy the code
   into the registration or recovery page.

## 4. Build and deploy

- In NetBeans: right-click the project → **Clean and Build** to produce the
  `.war`, then deploy that `.war` to the standalone GlassFish server (Services →
  Servers → GlassFish → Deploy, or `asadmin deploy dist/<name>.war`).
- Open the application: `http://localhost:8080/<context-root>/` → the welcome
  page offers **Login** and **Registration**.

## 5. First run (also the documented test dataset)

1. Register a user (via email verification) and log in.
2. Create at least **2 laptops** and **2 phones**.
3. Create **2 customers**.
4. For each customer, create **2 orders** (one laptop, one phone) — note the
   product stock drops by the ordered quantity.
5. Exercise list, search, view-details, and delete-order (stock is restored on
   delete).
6. Log out; type a function URL directly (e.g. `newPhone.xhtml`) to confirm the
   login filter redirects to the login page.

## Project structure

- `src/java/authentication/…` — security tier (registration, login, recovery,
  the login filter). Reused from the Week 10 project; the filter now protects
  every function page (whitelist of public pages only).
- `src/java/store/entities/…` — JPA entities: `Product` (JOINED super class),
  `Laptop`, `SmartPhone`, `Customer`, `Order`.
- `src/java/store/ejb/…` — EJBs holding all business logic (including stock
  management and order totals).
- `src/java/store/controllers/…` — JSF backing beans.
- `web/…` — Facelets (xhtml) pages using the shared `layout.xhtml` template.
