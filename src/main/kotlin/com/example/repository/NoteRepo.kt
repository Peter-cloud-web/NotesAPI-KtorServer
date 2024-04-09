package com.example.repository

import com.example.data.model.note_model.Note
import com.example.data.table.NoteTable
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class NoteRepo {
    suspend fun addNote(note: Note, email: String) {
        dbQuery {
            NoteTable.insert { noteTable ->
                noteTable[NoteTable.id] = note.id
                noteTable[NoteTable.userEmail] = email
                noteTable[NoteTable.noteTitle] = note.noteTitle
                noteTable[NoteTable.description] = note.description
                noteTable[NoteTable.date] = note.date
            }
        }
    }

    suspend fun getAllNotes(email: String): List<Note> = dbQuery {
        NoteTable.selectAll().where { NoteTable.userEmail.eq(email) }
            .mapNotNull {
                rowToNote(it)
            }
    }

    suspend fun updateNote(note: Note, email: String) {
        dbQuery {
            NoteTable.update(
                where = {
                    NoteTable.userEmail.eq(email) and NoteTable.id.eq(note.id)
                }
            ) { noteTable ->
                noteTable[NoteTable.noteTitle] = note.noteTitle
                noteTable[NoteTable.description] = note.description
                noteTable[NoteTable.date] = note.date
            }
        }
    }

    suspend fun deleteNote(id: String,email: String) {
        dbQuery {
            NoteTable.deleteWhere { NoteTable.userEmail.eq(email) and NoteTable.id.eq(id) }
        }
    }
}


private fun rowToNote(row: ResultRow): Note? {
    if (row == null) {
        return null
    }
    return Note(
        id = row[NoteTable.id],
        noteTitle = row[NoteTable.noteTitle],
        description = row[NoteTable.description],
        date = row[NoteTable.date]
    )
}