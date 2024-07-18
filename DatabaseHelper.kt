package com.example.wellness_app

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "wellness_app.db"

        @Volatile
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
            }
            return instance!!
        }
        // Tables
        const val TABLE_PATIENTS = "Patient"
        const val TABLE_THERAPISTS = "Therapists"
        const val TABLE_APPOINTMENTS = "Appointments"
        const val TABLE_DEPARTMENTS = "Departments"
        const val TABLE_REVIEWS = "Reviews"
        const val TABLE_THERAPIST_CATEGORIES ="Therapist_Categories"
        const val TABLE_PATIENT_THERAPIST ="Patient_Therapist"
        const val TABLE_KNOWLEDGE = "Knowledge"
        const val TABLE_MOOD_ENTRIES = "mood_entries"

        //MOOD ENTRIES
        const val COLUMN_MOOD_ENTRY_ID = "mood_entry_id"
        const val COLUMN_MOOD_P_ID = "P_id"
        const val COLUMN_MOOD = "mood"
        const val COLUMN_DIARY = "diary"
        const val COLUMN_TIMESTAMP = "timestamp"


        // Columns for Patients table
        const val COLUMN_P_ID = "P_id"
        const val COLUMN_P_NAME = "P_name"
        const val COLUMN_P_USERNAME = "P_username"
        const val COLUMN_P_EMAIL = "P_email"
        const val COLUMN_P_PASSWORD = "P_password"
        const val COLUMN_P_CONTACT = "P_contact"
        const val COLUMN_P_NIN = "P_nin"

        // Columns for Therapists table
        const val COLUMN_T_ID = "T_id"
        const val COLUMN_T_USERNAME = "T_username"
        const val COLUMN_T_NAME = "T_name"
        const val COLUMN_T_NIN = "T_nin"
        const val COLUMN_T_EMAIL = "T_email"
        const val COLUMN_T_PASSWORD = "T_password"
        const val COLUMN_T_CONTACT = "T_contact"

        // Columns for Appointments table
        const val COLUMN_A_ID = "A_id"
        const val COLUMN_A_P_ID = "A_P_id"
        const val COLUMN_A_T_ID = "A_T_id"
        const val COLUMN_A_DATE = "A_date"
        const val COLUMN_A_TIME = "A_time"

        // Columns for Departments table
        const val COLUMN_D_ID = "D_id"
        const val COLUMN_D_NAME = "D_name"

        // Columns for Reviews table
        const val COLUMN_R_ID = "R_id"
        const val COLUMN_R_P_ID = "R_P_id"
        const val COLUMN_R_T_ID = "R_T_id"
        const val COLUMN_REVIEW = "R_review"

        //Columns for Therapist_Categories
        const val COLUMN_TC_ID ="TC_ID"
        const val COLUMN_TC_T_ID = "TC_T_ID"
        const val COLUMN_TC_D_ID ="TC_D_ID"

        //columns for Patient_Therapy
        const val COLUMN_PC_ID="PC_ID"
        const val COLUMN_PC_P_ID="PC_P_ID"
        const val COLUMN_PC_T_ID="PC_T_ID"


        // Columns for Knowledge table
        const val COLUMN_K_ID = "K_id"
        const val COLUMN_K_D_ID = "K_D_id"
        const val COLUMN_K_CONTENT = "K_content"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create tables
        db.execSQL("CREATE TABLE $TABLE_PATIENTS (" +
                "$COLUMN_P_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_P_NAME TEXT," +
                "$COLUMN_P_USERNAME TEXT," +
                "$COLUMN_P_EMAIL TEXT," +
                "$COLUMN_P_PASSWORD TEXT," +
                "$COLUMN_P_CONTACT TEXT," +
                "$COLUMN_P_NIN TEXT)")

        db.execSQL("CREATE TABLE $TABLE_THERAPISTS (" +
                "$COLUMN_T_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_T_USERNAME TEXT," +
                "$COLUMN_T_NAME TEXT," +
                "$COLUMN_T_NIN TEXT," +
                "$COLUMN_T_EMAIL TEXT," +
                "$COLUMN_T_PASSWORD TEXT," +
                "$COLUMN_T_CONTACT TEXT)")

        db.execSQL("CREATE TABLE $TABLE_APPOINTMENTS (" +
                "$COLUMN_A_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_A_P_ID INTEGER," +
                "$COLUMN_A_T_ID INTEGER," +
                "$COLUMN_A_DATE TEXT," +
                "$COLUMN_A_TIME TEXT," +
                "FOREIGN KEY($COLUMN_A_P_ID) REFERENCES $TABLE_PATIENTS($COLUMN_P_ID)," +
                "FOREIGN KEY($COLUMN_A_T_ID) REFERENCES $TABLE_THERAPISTS($COLUMN_T_ID))")

        db.execSQL("CREATE TABLE $TABLE_DEPARTMENTS (" +
                "$COLUMN_D_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_D_NAME TEXT)")

        db.execSQL("CREATE TABLE $TABLE_REVIEWS (" +
                "$COLUMN_R_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_R_P_ID INTEGER," +
                "$COLUMN_R_T_ID INTEGER," +
                "$COLUMN_REVIEW TEXT," +
                "FOREIGN KEY($COLUMN_R_P_ID) REFERENCES $TABLE_PATIENTS($COLUMN_P_ID)," +
                "FOREIGN KEY($COLUMN_R_T_ID) REFERENCES $TABLE_THERAPISTS($COLUMN_T_ID))")


        db.execSQL("CREATE TABLE $TABLE_THERAPIST_CATEGORIES (" +
                "$COLUMN_TC_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TC_T_ID INTEGER," +
                "$COLUMN_TC_D_ID INTEGER," +
                "FOREIGN KEY($COLUMN_TC_D_ID) REFERENCES $TABLE_DEPARTMENTS($COLUMN_D_ID)," +
                "FOREIGN KEY($COLUMN_TC_T_ID) REFERENCES $TABLE_THERAPISTS($COLUMN_T_ID))")


        db.execSQL("CREATE TABLE $TABLE_PATIENT_THERAPIST (" +
                "$COLUMN_PC_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_PC_P_ID INTEGER," +
                "$COLUMN_PC_T_ID INTEGER," +
                "FOREIGN KEY($COLUMN_PC_P_ID) REFERENCES $TABLE_PATIENTS ($COLUMN_P_ID)," +
                "FOREIGN KEY($COLUMN_PC_T_ID) REFERENCES $TABLE_THERAPISTS($COLUMN_T_ID))")


        db.execSQL("CREATE TABLE $TABLE_KNOWLEDGE (" +
                "$COLUMN_K_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_K_D_ID INTEGER," +
                "$COLUMN_K_CONTENT TEXT," +
                "FOREIGN KEY($COLUMN_K_D_ID) REFERENCES $TABLE_DEPARTMENTS($COLUMN_D_ID))")

        db?.execSQL(
            "CREATE TABLE $TABLE_MOOD_ENTRIES(" +
                    "$COLUMN_MOOD_ENTRY_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_MOOD_P_ID TEXT," +
                    "$COLUMN_MOOD TEXT," +
                    "$COLUMN_DIARY TEXT," +
                    "$COLUMN_TIMESTAMP INTEGER)"
        )

    }




override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Upgrade database if needed
        // Not implemented in this example
    }


    // Insert methods

    fun insertPatient(patient: Patient): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_P_USERNAME, patient.P_Username)
            put(COLUMN_P_NAME, patient.P_name)
            put(COLUMN_P_NIN, patient.P_nin)
            put(COLUMN_P_EMAIL, patient.P_email)
            put(COLUMN_P_PASSWORD, patient.P_password)
            put(COLUMN_P_CONTACT, patient.P_contact)
        }
        return db.insert(TABLE_PATIENTS, null, contentValues)
    }



    fun insertTherapist(therapist: Therapist): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_T_USERNAME, therapist.T_username)
            put(COLUMN_T_NAME, therapist.T_name)
            put(COLUMN_T_NIN, therapist.T_nin)
            put(COLUMN_T_EMAIL, therapist.T_email)
            put(COLUMN_T_PASSWORD, therapist.T_password)
            put(COLUMN_T_CONTACT, therapist.T_contact)
        }
        return db.insert(TABLE_THERAPISTS, null, values)
    }



    suspend fun insertAppointment(appointment: Appointment): Long {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_A_P_ID, appointment.A_P_id)
                put(COLUMN_A_T_ID, appointment.A_T_id)
                put(COLUMN_A_DATE, appointment.A_date)
                put(COLUMN_A_TIME, appointment.A_time)
            }
            db.insert(TABLE_APPOINTMENTS, null, values)
        }
    }

    suspend fun insertDepartment(department: Department): Long {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_D_NAME, department.D_name)
            }
            db.insert(TABLE_DEPARTMENTS, null, values)
        }
    }

    // Fetch methods

    suspend fun getAllPatients(): List<Patient> {
        return withContext(Dispatchers.IO) {
            val patients = mutableListOf<Patient>()
            val db = this@DatabaseHelper.readableDatabase
            val query = "SELECT * FROM $TABLE_PATIENTS"
            val cursor = db.rawQuery(query, null)
            cursor.use {
                while (it.moveToNext()) {
                    val patient = Patient(
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_P_ID)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_P_NAME)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_P_USERNAME)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_P_EMAIL)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_P_PASSWORD)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_P_CONTACT)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_P_NIN))
                    )
                    patients.add(patient)
                }
            }
            patients
        }
    }

    fun getAllTherapists(): List<Therapist> {
        val therapists = mutableListOf<Therapist>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_THERAPISTS"
        val cursor = db.rawQuery(query, null)
        cursor.use {
            while (it.moveToNext()) {
                val therapist = Therapist(
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_T_ID)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_T_USERNAME)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_T_NAME)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_T_NIN)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_T_EMAIL)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_T_PASSWORD)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_T_CONTACT))
                )
                therapists.add(therapist)
            }
        }
        return therapists
    }


    suspend fun getAllAppointments(): List<Appointment> {
        return withContext(Dispatchers.IO) {
            val appointments = mutableListOf<Appointment>()
            val db = this@DatabaseHelper.readableDatabase
            val query = "SELECT * FROM $TABLE_APPOINTMENTS"
            val cursor = db.rawQuery(query, null)
            cursor.use {
                while (it.moveToNext()) {
                    val appointment = Appointment(
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_A_ID)),
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_A_P_ID)),
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_A_T_ID)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_A_DATE)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_A_TIME))
                    )
                    appointments.add(appointment)
                }
            }
            appointments
        }
    }



    suspend fun getPatientByName(name: String): Patient? {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.readableDatabase
            val query = "SELECT * FROM $TABLE_PATIENTS WHERE $COLUMN_P_NAME = ?"
            val cursor = db.rawQuery(query, arrayOf(name))
            cursor.use {
                if (it.moveToFirst()) {
                    Patient(
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_P_ID)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_P_NAME)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_P_USERNAME)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_P_EMAIL)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_P_PASSWORD)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_P_CONTACT)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_P_NIN))
                    )
                } else {
                    null
                }
            }
        }
    }

    suspend fun getTherapistByName(name: String): Therapist? {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.readableDatabase
            val query = "SELECT * FROM $TABLE_THERAPISTS WHERE $COLUMN_T_NAME = ?"
            val cursor = db.rawQuery(query, arrayOf(name))
            cursor.use {
                if (it.moveToFirst()) {
                    Therapist(
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_T_ID)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_T_USERNAME)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_T_NAME)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_T_NIN)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_T_EMAIL)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_T_PASSWORD)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_T_CONTACT))
                    )
                } else {
                    null
                }
            }
        }
    }

    suspend fun getAppointmentsWithPatientAndTherapist(): List<Appointment> {
        return withContext(Dispatchers.IO) {
            val appointments = mutableListOf<Appointment>()
            val db = this@DatabaseHelper.readableDatabase
            val query = "SELECT " +
                    "$TABLE_APPOINTMENTS.$COLUMN_A_ID, " +
                    "$TABLE_APPOINTMENTS.$COLUMN_A_P_ID, " +
                    "$TABLE_APPOINTMENTS.$COLUMN_A_T_ID, " +
                    "$TABLE_APPOINTMENTS.$COLUMN_A_DATE, " +
                    "$TABLE_APPOINTMENTS.$COLUMN_A_TIME, " +
                    "$TABLE_PATIENTS.$COLUMN_P_NAME AS patient_name, " +
                    "$TABLE_THERAPISTS.$COLUMN_T_NAME AS therapist_name " +
                    "FROM $TABLE_APPOINTMENTS " +
                    "INNER JOIN $TABLE_PATIENTS ON $TABLE_APPOINTMENTS.$COLUMN_A_P_ID = $TABLE_PATIENTS.$COLUMN_P_ID " +
                    "INNER JOIN $TABLE_THERAPISTS ON $TABLE_APPOINTMENTS.$COLUMN_A_T_ID = $TABLE_THERAPISTS.$COLUMN_T_ID"
            val cursor = db.rawQuery(query, null)
            cursor.use {
                while (it.moveToNext()) {
                    val appointment = Appointment(
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_A_ID)),
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_A_P_ID)),
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_A_T_ID)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_A_DATE)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_A_TIME))
                    )
                    appointments.add(appointment)
                }
            }
            appointments
        }
    }


    // Update methods

    suspend fun updatePatient(patient: Patient): Int {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_P_NAME, patient.P_name)
                put(COLUMN_P_USERNAME, patient.P_Username)
                put(COLUMN_P_EMAIL, patient.P_email)
                put(COLUMN_P_PASSWORD, patient.P_password)
                put(COLUMN_P_CONTACT, patient.P_contact)
                put(COLUMN_P_NIN, patient.P_nin)
            }
            val selection = "$COLUMN_P_ID = ?"
            val selectionArgs = arrayOf(patient.P_id.toString())
            db.update(TABLE_PATIENTS, values, selection, selectionArgs)
        }
    }

    suspend fun updateTherapist(therapist: Therapist): Int {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_T_USERNAME, therapist.T_username)
                put(COLUMN_T_NAME, therapist.T_name)
                put(COLUMN_T_NIN, therapist.T_nin)
                put(COLUMN_T_EMAIL, therapist.T_email)
                put(COLUMN_T_PASSWORD, therapist.T_password)
                put(COLUMN_T_CONTACT, therapist.T_contact)
            }
            val selection = "$COLUMN_T_ID = ?"
            val selectionArgs = arrayOf(therapist.T_id.toString())
            db.update(TABLE_THERAPISTS, values, selection, selectionArgs)
        }
    }

    suspend fun updateAppointment(appointment: Appointment): Int {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_A_P_ID, appointment.A_P_id)
                put(COLUMN_A_T_ID, appointment.A_T_id)
                put(COLUMN_A_DATE, appointment.A_date)
                put(COLUMN_A_TIME, appointment.A_time)
            }
            val selection = "$COLUMN_A_ID = ?"
            val selectionArgs = arrayOf(appointment.A_id.toString())
            db.update(TABLE_APPOINTMENTS, values, selection, selectionArgs)
        }
    }

    suspend fun updateDepartment(department: Department): Int {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_D_NAME, department.D_name)
            }
            val selection = "$COLUMN_D_ID = ?"
            val selectionArgs = arrayOf(department.D_id.toString())
            db.update(TABLE_DEPARTMENTS, values, selection, selectionArgs)
        }
    }

    // Delete methods

    suspend fun deletePatient(patientId: Int): Int {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.writableDatabase
            val selection = "$COLUMN_P_ID = ?"
            val selectionArgs = arrayOf(patientId.toString())
            db.delete(TABLE_PATIENTS, selection, selectionArgs)
        }
    }

    suspend fun deleteTherapist(therapistId: Int): Int {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.writableDatabase
            val selection = "$COLUMN_T_ID = ?"
            val selectionArgs = arrayOf(therapistId.toString())
            db.delete(TABLE_THERAPISTS, selection, selectionArgs)
        }
    }

    suspend fun deleteAppointment(appointmentId: Int): Int {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.writableDatabase
            val selection = "$COLUMN_A_ID = ?"
            val selectionArgs = arrayOf(appointmentId.toString())
            db.delete(TABLE_APPOINTMENTS, selection, selectionArgs)
        }
    }

    suspend fun deleteDepartment(departmentId: Int): Int {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.writableDatabase
            val selection = "$COLUMN_D_ID = ?"
            val selectionArgs = arrayOf(departmentId.toString())
            db.delete(TABLE_DEPARTMENTS, selection, selectionArgs)
        }
    }


    fun getAppointmentsForPatientFromToday(patientId: Int): List<Appointment> {
        val appointments = mutableListOf<Appointment>()
        val currentDate = Calendar.getInstance().timeInMillis

        val db = this.readableDatabase
        val query = """
            SELECT A.$COLUMN_A_ID, A.$COLUMN_A_P_ID, A.$COLUMN_A_T_ID, A.$COLUMN_A_DATE, A.$COLUMN_A_TIME, 
                   P.$COLUMN_P_NAME AS patientName, 
                   T.$COLUMN_T_NAME AS therapistName 
            FROM $TABLE_APPOINTMENTS A
            INNER JOIN $TABLE_PATIENTS P ON A.$COLUMN_A_P_ID = P.$COLUMN_P_ID
            INNER JOIN $TABLE_THERAPISTS T ON A.$COLUMN_A_T_ID = T.$COLUMN_T_ID
            WHERE A.$COLUMN_A_P_ID = ? AND A.$COLUMN_A_DATE >= ?
            ORDER BY A.$COLUMN_A_DATE ASC, A.$COLUMN_A_TIME ASC
        """
        val cursor = db.rawQuery(query, arrayOf(patientId.toString(), currentDate.toString()))
        cursor.use {
            while (it.moveToNext()) {
                val appointmentId = it.getInt(it.getColumnIndexOrThrow(COLUMN_A_ID))
                val patientId = it.getInt(it.getColumnIndexOrThrow(COLUMN_A_P_ID))
                val therapistId = it.getInt(it.getColumnIndexOrThrow(COLUMN_A_T_ID))
                val date = it.getString(it.getColumnIndexOrThrow(COLUMN_A_DATE))
                val time = it.getString(it.getColumnIndexOrThrow(COLUMN_A_TIME))
                val patientName = it.getString(it.getColumnIndexOrThrow("patientName"))
                val therapistName = it.getString(it.getColumnIndexOrThrow("therapistName"))

                val appointment = Appointment(appointmentId, patientId, therapistId, date, time, patientName, therapistName)
                appointments.add(appointment)
            }
        }
        return appointments
    }

// In DatabaseHelper.kt

    suspend fun insertReview(review: Reviews): Long {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.writableDatabase
            val contentValues = ContentValues().apply {
                put(COLUMN_R_P_ID, review.R_P_id)
                put(COLUMN_R_T_ID, review.R_T_id)
                put(COLUMN_REVIEW, review.reviewText)
            }
            return@withContext db.insert(TABLE_REVIEWS, null, contentValues)
        }
    }

    suspend fun getAllReviewsWithDetails(): List<ReviewWithDetails> = withContext(Dispatchers.IO) {
        val reviews = mutableListOf<ReviewWithDetails>()
        val selectQuery = "SELECT $TABLE_REVIEWS.$COLUMN_R_ID, " +
                "$TABLE_REVIEWS.$COLUMN_REVIEW, " +
                "$TABLE_PATIENTS.$COLUMN_P_USERNAME, " +
                "$TABLE_THERAPISTS.$COLUMN_T_NAME " +
                "FROM $TABLE_REVIEWS " +
                "INNER JOIN $TABLE_PATIENTS ON $TABLE_REVIEWS.$COLUMN_R_P_ID = $TABLE_PATIENTS.$COLUMN_P_ID " +
                "INNER JOIN $TABLE_THERAPISTS ON $TABLE_REVIEWS.$COLUMN_R_T_ID = $TABLE_THERAPISTS.$COLUMN_T_ID"

        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        cursor.use { cursor ->
            while (cursor.moveToNext()) {
                val reviewIdIndex = cursor.getColumnIndex(COLUMN_R_ID)
                val reviewTextIndex = cursor.getColumnIndex(COLUMN_REVIEW)
                val patientUsernameIndex = cursor.getColumnIndex(COLUMN_P_USERNAME)
                val therapistNameIndex = cursor.getColumnIndex(COLUMN_T_NAME)

                // Check if indices are valid before accessing values
                if (reviewIdIndex >= 0 && reviewTextIndex >= 0 && patientUsernameIndex >= 0 && therapistNameIndex >= 0) {
                    val reviewId = cursor.getInt(reviewIdIndex)
                    val reviewText = cursor.getString(reviewTextIndex)
                    val patientUsername = cursor.getString(patientUsernameIndex)
                    val therapistName = cursor.getString(therapistNameIndex)

                    val reviewWithDetails = ReviewWithDetails(reviewId, reviewText, patientUsername, therapistName)
                    reviews.add(reviewWithDetails)
                } else {
                    // Handle error or log a message if indices are not valid
                    // This part depends on your error handling strategy
                    // Example:
                    // Log.e("DatabaseHelper", "Column index out of bounds")
                }
            }
        }

        return@withContext reviews
    }



    suspend fun getPatientByUsername(username: String): Patient? {
        return withContext(Dispatchers.IO) {
            val db = this@DatabaseHelper.readableDatabase
            val cursor = db.query(
                TABLE_PATIENTS,
                arrayOf(
                    COLUMN_P_ID, COLUMN_P_NAME, COLUMN_P_USERNAME,
                    COLUMN_P_EMAIL, COLUMN_P_NIN, COLUMN_P_PASSWORD,
                    COLUMN_P_CONTACT
                ),
                "$COLUMN_P_USERNAME = ?",
                arrayOf(username),
                null,
                null,
                null
            )

            var patient: Patient? = null
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    patient = Patient(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_P_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_NIN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_PASSWORD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_CONTACT))
                    )
                }
                cursor.close()
            }
            patient
        }
    }


    suspend fun getTherapistCategories(): List<TherapistCategory> = withContext(Dispatchers.IO) {
        val db = readableDatabase
        val query = """
            SELECT t.$COLUMN_T_NAME, d.$COLUMN_D_NAME
            FROM $TABLE_THERAPIST_CATEGORIES tc
            JOIN $TABLE_THERAPISTS t ON tc.$COLUMN_TC_T_ID = t.$COLUMN_T_ID
            JOIN $TABLE_DEPARTMENTS d ON tc.$COLUMN_TC_D_ID = d.$COLUMN_D_ID
        """
        val cursor = db.rawQuery(query, null)
        val categories = mutableListOf<TherapistCategory>()

        if (cursor.moveToFirst()) {
            do {
                val therapistName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_T_NAME))
                val departmentName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_D_NAME))
                categories.add(TherapistCategory(therapistName, departmentName))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return@withContext categories
    }
    // Function to add knowledge entry


    // Function to get all departments





    data class Appointments(
        val patientId: Int,
        val date: String,
        val time: String,
        var patientName: String // Add patientName as a var property
    )

    fun getAppointmentsByTherapistId(therapistId: Int): List<Appointments> {
        val appointments = mutableListOf<Appointments>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_A_P_ID, $COLUMN_A_DATE, $COLUMN_A_TIME FROM $TABLE_APPOINTMENTS WHERE $COLUMN_A_T_ID = ?", arrayOf(therapistId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val patientId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_A_P_ID))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_A_DATE))
                val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_A_TIME))
                val patientName = getPatientNameById(patientId) // Fetch patient name here
                appointments.add(Appointments(patientId, date, time, patientName))
            } while (cursor.moveToNext())
        }
        cursor.close()

        return appointments
    }


    fun getPatientNameById(patientId: Int): String {
        var patientName = ""
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_P_NAME FROM $TABLE_PATIENTS WHERE $COLUMN_P_ID = ?", arrayOf(patientId.toString()))

        if (cursor.moveToFirst()) {
            patientName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_NAME))
        }
        cursor.close()
        return patientName
    }

    fun getPatientsForTherapist(therapistId: Int): List<Patient> {
        val patients = mutableListOf<Patient>()
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_P_ID, $COLUMN_P_NAME, $COLUMN_P_USERNAME, $COLUMN_P_EMAIL, $COLUMN_P_PASSWORD, $COLUMN_P_CONTACT, $COLUMN_P_NIN " +
                "FROM $TABLE_PATIENTS " +
                "WHERE $COLUMN_P_ID IN " +
                "(SELECT $COLUMN_PC_P_ID FROM $TABLE_PATIENT_THERAPIST WHERE $COLUMN_PC_T_ID = ?)"

        val cursor = db.rawQuery(query, arrayOf(therapistId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val patientId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_P_ID))
                val patientName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_NAME))
                val patientUsername = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_USERNAME))
                val patientEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_EMAIL))
                val patientPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_PASSWORD))
                val patientContact = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_CONTACT))
                val patientNin = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_P_NIN))

                val patient = Patient(patientId, patientName, patientUsername, patientEmail, patientPassword, patientContact, patientNin)
                patients.add(patient)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return patients
    }
    fun getAllDepartments(): List<Department> {
        val departments = mutableListOf<Department>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT D_id, D_name FROM Departments", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("D_id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("D_name"))
                departments.add(Department(id, name))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return departments
    }
    fun addKnowledgeEntry(dId: Int, content: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("department_id", dId)
            put("content", content)
        }
        db.insert("KnowledgeEntries", null, values)
    }
    fun insertMoodEntry(moodEntry: MoodEntry): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MOOD_P_ID, moodEntry.P_id)
            put(COLUMN_MOOD, moodEntry.mood)
            put(COLUMN_DIARY, moodEntry.diary)
            put(COLUMN_TIMESTAMP, moodEntry.timestamp)
        }
        val result = db.insert(TABLE_MOOD_ENTRIES, null, values)
        db.close()
        return result
    }

    fun getAllMoodEntries(): List<MoodEntry> {
        val moodEntries = mutableListOf<MoodEntry>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_MOOD_ENTRIES,
            null,
            null,
            null,
            null,
            null,
            null
        )
        cursor.use {
            while (it.moveToNext()) {
                val userId = it.getString(it.getColumnIndexOrThrow(COLUMN_MOOD_P_ID))
                val mood = it.getString(it.getColumnIndexOrThrow(COLUMN_MOOD))
                val diary = it.getString(it.getColumnIndexOrThrow(COLUMN_DIARY))
                val timestamp = it.getLong(it.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                val moodEntry = MoodEntry(userId, mood, diary, timestamp)
                moodEntries.add(moodEntry)
            }
        }
        cursor.close()
        db.close()
        return moodEntries
    }


    fun getAppointmentsForPatientBeforeToday(patientId: Int): List<Appointment> {
        val appointments = mutableListOf<Appointment>()
        val currentDate = Calendar.getInstance().timeInMillis

        val db = this.readableDatabase
        val query = """
            SELECT A.$COLUMN_A_ID, A.$COLUMN_A_P_ID, A.$COLUMN_A_T_ID, A.$COLUMN_A_DATE, A.$COLUMN_A_TIME, 
                   P.$COLUMN_P_NAME AS patientName, 
                   T.$COLUMN_T_NAME AS therapistName 
            FROM $TABLE_APPOINTMENTS A
            INNER JOIN $TABLE_PATIENTS P ON A.$COLUMN_A_P_ID = P.$COLUMN_P_ID
            INNER JOIN $TABLE_THERAPISTS T ON A.$COLUMN_A_T_ID = T.$COLUMN_T_ID
            WHERE A.$COLUMN_A_P_ID = ? AND A.$COLUMN_A_DATE < ?
            ORDER BY A.$COLUMN_A_DATE DESC, A.$COLUMN_A_TIME DESC
        """
        val cursor = db.rawQuery(query, arrayOf(patientId.toString(), currentDate.toString()))
        cursor.use {
            while (it.moveToNext()) {
                val appointmentId = it.getInt(it.getColumnIndexOrThrow(COLUMN_A_ID))
                val patientId = it.getInt(it.getColumnIndexOrThrow(COLUMN_A_P_ID))
                val therapistId = it.getInt(it.getColumnIndexOrThrow(COLUMN_A_T_ID))
                val date = it.getString(it.getColumnIndexOrThrow(COLUMN_A_DATE))
                val time = it.getString(it.getColumnIndexOrThrow(COLUMN_A_TIME))
                val patientName = it.getString(it.getColumnIndexOrThrow("patientName"))
                val therapistName = it.getString(it.getColumnIndexOrThrow("therapistName"))

                val appointment = Appointment(appointmentId, patientId, therapistId, date, time, patientName, therapistName)
                appointments.add(appointment)
            }
        }
        return appointments
    }
    suspend fun insertTherapistCategories(therapistId: Int, departmentIds: List<Int>) {
        withContext(Dispatchers.IO) {
            writableDatabase.beginTransaction()
            try {
                departmentIds.forEach { departmentId ->
                    val values = ContentValues().apply {
                        put(COLUMN_TC_T_ID, therapistId)
                        put(COLUMN_TC_D_ID, departmentId)
                    }
                    writableDatabase.insert(TABLE_THERAPIST_CATEGORIES, null, values)
                }
                writableDatabase.setTransactionSuccessful()
            } finally {
                writableDatabase.endTransaction()
            }
        }
    }
    @SuppressLint("Range")
    fun getTherapistCategoriesWithDetails(): List<TherapistCategory> {
        val categories = mutableListOf<TherapistCategory>()
        val query = """
        SELECT Therapist.T_name, Departments.D_name
        FROM TherapistCategory
        INNER JOIN Therapist ON TherapistCategory.TC_T_ID = Therapist.T_id
        INNER JOIN Departments ON TherapistCategory.TC_D_ID = Departments.D_id
    """.trimIndent()

        val db = readableDatabase
        val cursor = db.rawQuery(query, null)

        cursor.use { cursor ->
            val therapistNameIndex = cursor.getColumnIndex("T_name")
            val departmentNameIndex = cursor.getColumnIndex("D_name")

            while (cursor.moveToNext()) {
                val therapistName = cursor.getString(therapistNameIndex)
                val departmentName = cursor.getString(departmentNameIndex)

                val therapistCategory = TherapistCategory(therapistName, departmentName)
                categories.add(therapistCategory)
            }
        }

        return categories
    }

    fun addPatientTherapistRelation(patientId: Int, therapistId: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PC_P_ID, patientId)
            put(COLUMN_PC_T_ID, therapistId)
        }
        db.insert(TABLE_PATIENT_THERAPIST, null, values)
    }


    suspend fun getDepartmentIdByName(departmentName: String): Int? {
        return withContext(Dispatchers.IO) {
            val db = readableDatabase
            val cursor = db.rawQuery("SELECT $COLUMN_D_ID FROM $TABLE_DEPARTMENTS WHERE $COLUMN_D_NAME = ?", arrayOf(departmentName))
            var departmentId: Int? = null
            if (cursor.moveToFirst()) {
                departmentId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_D_ID))
            }
            cursor.close()
            departmentId
        }
    }




}










