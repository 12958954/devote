package com.example.cs398project

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class Mysql {
    /*
    Database: test
    User: root
    Password: cs398
     */
    private var connect: Connection
    init {
        Class.forName("com.mysql.cj.jdbc.Driver")
        connect = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/test",
            "root",
            "password"
        )
    }
    fun query(code: String): ResultSet {
        return connect.createStatement().executeQuery(code)
    }
    fun stmt(code: String) {
        connect.createStatement().execute(code)
        //connect.commit()
    }
}

// Example: https://stonesoupprogramming.com/2017/12/13/kotlin-jdbc-create-insert-query-and-truncate-tables/
