package com.example.wellness_app

data class Patient(
    var P_id: Int = 0,
    var P_name: String = "",
    var P_Username: String = "",
    var P_email: String = "",
    var P_password: String = "",
    var P_contact: String = "",
    var P_nin: String = ""
)

data class Therapist(
    var T_id: Int = 0,
    var T_username: String = "",
    var T_name: String = "",
    var T_nin: String = "",
    var T_email: String = "",
    var T_password: String = "",
    var T_contact: String = ""
)

data class Appointment(
    val A_id: Int,
    val A_P_id: Int,
    val A_T_id: Int,
    val A_date: String,
    val A_time: String,
    var patientName: String? = null,
    var therapistName: String? = null
) {
    constructor(
        A_id: Int,
        A_P_ID: Int,
        A_T_ID: Int,
        A_date: String,
        A_time: String
    ) : this(A_id, A_P_ID, A_T_ID, A_date, A_date, null, null)
}



data class Department(
    val D_id: Int,
    val D_name: String
)


data class Reviews(
    var R_id: Int = 0,
    var R_P_id: Int = 0,
    var R_T_id: Int = 0,
    var reviewText: String = ""
)

data class TherapistCategory(
    var therapistName: String = "",
    var departmentName: String = "",
    var TC_ID: Int = 0,
    var TC_T_ID: Int = 0,
    var TC_D_ID: Int = 0
) {
    constructor(therapistName: String, departmentName: String) : this(therapistName, departmentName, 0, 0, 0)
}


data class PatientTherapist(
    var PC_ID: Int = 0,
    var PC_P_ID: String?,
    var PC_T_ID:  String?
)

data class ReviewWithDetails(
    val reviewId: Int,
    val reviewText: String,
    val patientUsername: String,
    val therapistName: String
)
data class MoodEntry(val P_id: String, val mood: String, val diary: String, val timestamp: Long)

data class Message(
    val id: Long,
    val text: String,
    val sender: String,
    val timestamp: Long
)



data class ChatMessage(
    val senderId: String = "",
    val message: String = "",
    val timestamp: Long = 0
)

data class GuidedMeditation(
    val id: Long,
    val title: String,
    val description: String,
    val videoUrl: String,
    val duration: Long,// Duration in milliseconds

)