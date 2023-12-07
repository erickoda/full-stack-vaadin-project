# OOP Final Project
## Overview
This is a Java webapp with Vaadin and SQL Database developed as the final project for our Object Oriented Programming class. The project consists of a Java webapp that interfaces with a SQL database to store and manipulate data from a fictional car renting company.
## Features
Features are split between Manager and Employee log-ins. All Emplyee functionalities can be accessed from the Manager log-in, and the Emplyee log-in cannot access Manager only features.
### Manager
- [X] Register new vehicles.
- [ ] Mark and justify existing vehicle exclusion.
- [ ] Change operational parameters. 
- [ ] Generate Client/Vehicle/Renting statistics.
### Employee
- [X] Register new client.
- [ ] Register new vehicle renting.
- [ ] Cancel new vehicle renting.
- [ ] Register vehicle take-out.
- [ ] Return rented vehicle.
## Installation and Execution
This code is only guaranteed to work on Linux machines. Windows and MacOS were not tested.

Clone the repo to your machine:
```
git clone https://github.com/erickoda/full-stack-vaadin-project.git
```

### Dependencies
- git
- sudo
- postgresql
- java 17
- pgAdmin4

You'll need to install postgresql, Java 17 and pgAdmin4:

Ubuntu: 
```
curl -fsS https://www.pgadmin.org/static/packages_pgadmin_org.pub | sudo gpg --dearmor -o /usr/share/keyrings/packages-pgadmin-org.gpg
sudo sh -c 'echo "deb [signed-by=/usr/share/keyrings/packages-pgadmin-org.gpg] https://ftp.postgresql.org/pub/pgadmin/pgadmin4/apt/$(lsb_release -cs) pgadmin4 main" > /etc/apt/sources.list.d/pgadmin4.list && apt update'
sudo apt update && sudo apt upgrade
sudo apt -y install postgresql openjdk-17-jdk pgadmin4-desktop`
```

If your installation fails, check the [official postgresql website](https://www.postgresql.org/download/linux/ubuntu/) on how to add the correct repositoriy, the [official Java website](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-linux-platforms.html#GUID-737A84E4-2EFF-4D38-8E60-3E29D1B884B8) on means to install Java 17, and the [official pgAdmin4 website](https://www.pgadmin.org/download/pgadmin-4-apt/) on how to install pgAdmin4.

After installing the necessary dependencies, run in your terminal of choice:
```
sudo -i -u postgres
psql
\password postgres
cleber123
cleber123
```
This sets your database password to the correct one.

explain how to configure pgadmin

### Running the Webapp

### Credits
Erick Oda, Lorenzo Dutra, Henrique Bloemer, Raphael Zoega.
