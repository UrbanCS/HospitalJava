Overview
This project implements a Virtual Hospital Management System using Object-Oriented Programming (OOP) principles. It is designed to model relationships between various hospital entities such as Physicians, Administrators, Volunteers, and Patients while enforcing encapsulation, abstraction, inheritance, and polymorphism.

🛠 Features & Functionalities

1️⃣ Object-Oriented Design
-Implements inheritance, composition, and aggregation to model hospital entities.
-Differentiates between abstract classes, interfaces, and concrete classes.
-Encapsulates data properly with private attributes and public getters/setters.

2️⃣ Hospital Operations
-Patient Management: Admit, Assign Physicians, Discharge.
-Physician Management: Hire, Resign, Assign Administrators.
-Volunteer Management: Hire, Resign, Assign to Physicians.
-Data Extraction: Retrieve sorted lists of Patients, Physicians, and Volunteers.

3️⃣ Exception Handling
-Prevent exceeding maximum capacity for patients, physicians, or volunteers.
-Handle cases when no suitable physician or volunteer is available.

4️⃣ Unit Testing
-JUnit tests (HospitalTest.java) validate correctness.
-Additional test cases ensure edge cases and constraints are met.

📌 Class Structure

Class	Description

Hospital: Manages hospital operations (admit/discharge patients, hire/resign staff).
Physician: Represents a doctor with a specific specialty (Immunology, Dermatology, Neurology).
Administrator:	Manages physicians; roles include Director and Physician Administrator.
Volunteer:	Unpaid workers assigned to physicians.
Patient:	Represents hospital patients with unique IDs.

🚀 How to Run

1. Clone the Repository
-git clone https://github.com/UrbanCS/HospitalJava.git
-cd HospitalJava

2. Open in Eclipse
-Import as an existing Java project.
-Run HospitalTest.java to verify functionality.
