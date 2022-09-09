package com.example.cs398project

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MysqlTest {
    var testDatabase = Mysql()

    @BeforeEach
    fun setUp() {
        // create the table test_folders
        testDatabase.stmt("create table test_folders\n" +
                "(\n" +
                "    id int,\n" +
                "    name  varchar(1024),           \n" +
                "    primary key (id)          \n" +
                ");\n" +
                "\n")

        // create the table test_notes
        testDatabase.stmt("create table test_notes\n" +
                "(\n" +
                "    id int,\n" +
                "    title  varchar(1024),           \n" +
                "    timeModified  datetime,           \n" +
                "    timeCreated  datetime,           \n" +
                "    content  longtext,         \n" +
                "    creator  varchar(1024),          \n" +
                "    folder  int,          \n" +
                "    primary key (id),          \n" +
                "    foreign key (folder) references test_folders(id)\n" +
                ");\n" +
                "\n")

        // create the table test_app
        testDatabase.stmt("create table test_app\n" +
                "(\n" +
                "    prefHeight double,\n" +
                "    prefWidth  double,           \n" +
                "    xPos  double,           \n" +
                "    yPos  double           \n" +
                ");\n" +
                "\n")
    }

    @AfterEach
    fun tearDown() {
        testDatabase.stmt("drop table test_notes;\n")
        testDatabase.stmt("drop table test_folders;\n")
        testDatabase.stmt("drop table test_app;\n")
    }

    @Test
    fun insertRowsInTables() {

        testDatabase.stmt("INSERT INTO test_folders \n" +
                "VALUES(1, 'root')")
        testDatabase.stmt("INSERT INTO test_notes \n" +
                "VALUES(1, 'test', null, null, 'TESTNOTE', 'root', 1 )")
        testDatabase.stmt("INSERT INTO test_app \n" +
                "VALUES(1,2,3,4)")

        val foldersQuery = testDatabase.query("SELECT * FROM test_notes")
        val notesQuery = testDatabase.query("SELECT * FROM test_notes")
        val appQuery = testDatabase.query("SELECT * FROM test_notes")

        var folders = ""
        while (foldersQuery.next()) {
            folders = (foldersQuery.metaData.getColumnName(1) + ':' + foldersQuery.getString(1) + " , " +
                    foldersQuery.metaData.getColumnName(2) + ':' + foldersQuery.getString(2))
        }
        var app = ""
        while (appQuery.next()) {
            app = (appQuery.metaData.getColumnName(1) + ':' + appQuery.getString(1) + " , " +
                    appQuery.metaData.getColumnName(2) + ':' + appQuery.getString(2) + " , " +
                    appQuery.metaData.getColumnName(3) + ':' + appQuery.getString(3) + " , " +
                    appQuery.metaData.getColumnName(4) + ':' + appQuery.getString(4))
        }
        var notes = ""
        while (notesQuery.next()) {
            notes = (notesQuery.metaData.getColumnName(1) + ':' + notesQuery.getString(1) + " , " +
                    notesQuery.metaData.getColumnName(2) + ':' + notesQuery.getString(2)  + " , " +
                    notesQuery.metaData.getColumnName(3) + ':' + notesQuery.getString(3) + " , " +
                    notesQuery.metaData.getColumnName(4) + ':' + notesQuery.getString(4) + " , " +
                    notesQuery.metaData.getColumnName(5) + ':' + notesQuery.getString(5) + " , " +
                    notesQuery.metaData.getColumnName(6) + ':' + notesQuery.getString(6) + " , " +
                    notesQuery.metaData.getColumnName(7) + ':' + notesQuery.getString(7))
        }

        assertEquals("id:1 , title:test", folders)
        assertEquals("id:1 , title:test , timeModified:null , timeCreated:null , content:TESTNOTE , creator:root , folder:1", notes)
        assertEquals("id:1 , title:test , timeModified:null , timeCreated:null", app)
    }

    @Test
    fun findDataInTable() {
        testDatabase.stmt("INSERT INTO test_folders VALUES(1, 'root')")
        testDatabase.stmt("INSERT INTO test_notes \n" +
                "VALUES(1, 'test', null, null, 'TESTNOTE', 'root', 1 )")

        val notesQuery = testDatabase.query("SELECT content FROM test_notes WHERE id=1")

        var notes = ""
        while (notesQuery.next()) {
            notes = (notesQuery.getString(1))
        }
        assertEquals("TESTNOTE", notes)
    }

    @Test
    fun deleteRowInTable() {
        testDatabase.stmt("INSERT INTO test_folders VALUES(1, 'root')")
        testDatabase.stmt("INSERT INTO test_notes \n" +
                "VALUES(1, 'test', null, null, 'TESTNOTE', 'root', 1 )")

        testDatabase.stmt("DELETE FROM test_notes WHERE id=1")
        val notesQuery = testDatabase.query("SELECT count(*) FROM test_notes")


        var nbrRows = ""
        while (notesQuery.next()) {
            nbrRows = (notesQuery.getString(1))
        }
        assertEquals("0", nbrRows)
    }

    @Test
    fun updateRowInTable() {
        testDatabase.stmt("INSERT INTO test_folders VALUES(1, 'root')")
        testDatabase.stmt("INSERT INTO test_notes \n" +
                "VALUES(1, 'test', null, null, 'TESTNOTE', 'root', 1 )")

        val beforeUpdateQuery = testDatabase.query("SELECT content FROM test_notes WHERE id=1")
        testDatabase.stmt("UPDATE test_notes SET content = 'UPDATED' WHERE id = 1")
        val afterUpdateQuery = testDatabase.query("SELECT content FROM test_notes WHERE id=1")

        var beforeUpdate = ""
        while (beforeUpdateQuery.next()) {
            beforeUpdate = (beforeUpdateQuery.getString(1))
        }
        var afterUpdate = ""
        while (afterUpdateQuery.next()) {
            beforeUpdate = (afterUpdateQuery.getString(1))
        }

        assertFalse(beforeUpdate == afterUpdate)
    }
}