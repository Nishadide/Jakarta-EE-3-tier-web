# COIT20259 Assignment 2 - e-Business System

A three-tier Jakarta EE web application for managing products (laptops and smart
phones), customers and orders, built for COIT20259 Enterprise Computing
Architecture (Term 2, 2026).

## Architecture

- **Presentation tier** - JavaServer Faces (Facelets) pages and `@Named` backing
  beans (`store.controllers`).
- **Business tier** - stateless Enterprise JavaBeans holding the business logic,
  including order totals and stock management (`store.ejb`).
- **Data tier** - Java Persistence API entities (`store.entities`) mapped to a
  MySQL database. `Product` is a superclass with `Laptop` and `SmartPhone`
  subclasses using the JOINED inheritance strategy; `Customer` has a one-to-many
  relationship to `Order`.
- **Security** - registration with email verification, login, logout and account
  recovery, with a servlet filter that protects every function page (public
  pages are whitelisted).

## Technology

Jakarta EE 10, JSF 4.0, EJB, JPA/EclipseLink, MySQL 8.x, GlassFish 7.0.9,
NetBeans (Ant build), FakeSMTP for email verification and recovery.

## Build and deploy

See [SETUP_AND_DEPLOYMENT.md](SETUP_AND_DEPLOYMENT.md) for the full instructions
(MySQL database, GlassFish JDBC connection pool and data source, FakeSMTP, and
building and deploying the WAR).

## Team

- [Team member 1, ID] (Team Leader)
- [Team member 2, ID]
- [Team member 3, ID]
- [Team member 4, ID]
