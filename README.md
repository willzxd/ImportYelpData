#Import Yelp Data To PostgreSQL
Update: 2015.10.23
## Overview
This repository helps you import most part of yelp acedemic data to your postgreSQL.

## Instruction

###Install maven
The official guide: <https://maven.apache.org/index.html>

###Install psql
The official guide: <http://www.postgresql.org/download/>

If you use a Mac, you can use brew to install it easily.
There are lost of instruction on the Internet.

A simple instruction is here:

Install with homebrew:
`$ brew install postgresql -v`

Init psql:
`$ initdb /usr/local/var/postgres -E utf8`

Run psql automatically:
```shell
$ ln -sfv /usr/local/opt/postgresql/*.plist ~/Library/LaunchAgents
$ launchctl load ~/Library/LaunchAgents/homebrew.mxcl.postgresql.plist
```

Run qsql:

`$ pg_ctl -D /usr/local/var/postgres -l /usr/local/var/postgres/server.log start`

Also you can install a interface for psql. I recommand pgAdmin. You can find it here: <http://www.pgadmin.org> 

###Download yelp acedemic data
challenge site: <http://www.yelp.com/dataset_challenge/>

academic dataset site: <https://www.yelp.com/academic_dataset>

###Create a dbuser and database
How to create a user: <http://www.postgresql.org/docs/9.2/static/app-createuser.html>

Simple instruction

Create a user:
```
$ createuser username -P
#Enter password for new role:
#Enter it again:
```

Create a database:
```
$ createdb dbname -O username -E UTF8 -e
```

###Setup
Download this repo.

You can set up the path of datafiles, db_username, db_password db_name and db_connection in run.sh

###Execution
under the repo directory, execute ```$ . run.sh```
    