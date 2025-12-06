🏧 ATM Simulation System 

A modern Java-based ATM simulator with a full JavaFX GUI, secure PIN hashing, transaction history tracking, and multi-step card registration — all powered by a real database backend.

<p align="center"> <img src="https://img.shields.io/badge/Java-17+-red?style=for-the-badge&logo=openjdk" /> <img src="https://img.shields.io/badge/JavaFX-Modern UI-blue?style=for-the-badge&logo=javafx" /> <img src="https://img.shields.io/badge/ATM-Simulation-brightgreen?style=for-the-badge" /> <img src="https://img.shields.io/badge/Database-SQL-blueviolet?style=for-the-badge&logo=postgresql" /> <img src="https://img.shields.io/badge/Security-SHA%20Hash-yellow?style=for-the-badge" /> </p>
🌐 1. Overview

This project is a full ATM machine simulation, entirely built in Java with a JavaFX user interface.
No hardware is involved — the application emulates how a real ATM behaves from a software perspective.

The program covers card registration, card login, balance operations, and transaction tracking, supported by a real relational database.

🖥️ 2. Core Features
🔐 Secure Login (ATM Card Simulation)

The login window simulates inserting a credit/debit card into an ATM:

Enter 16-digit card number

Enter PIN code

System validates and logs you in if both match

Your PIN code is hashed (SHA) before being stored for security.

🆕 New Card Registration (4-Step Wizard)

If a user does not have a card, they can create one through 4 interactive forms:

Personal information

Contact details

Financial information

Security details (PIN creation)

After successful registration:

A credit or debit card is created

PIN is hashed and stored

Card becomes immediately usable in the login screen

💵 ATM Operations

Once logged in, users get access to all typical ATM functionalities:

✔ Withdraw Money

Enter custom amount or

Use Fast Withdraw buttons (common fixed amounts)

✔ Deposit Money

Enter any amount to deposit

Balance immediately updates

✔ Change PIN

Enter old PIN

Enter new PIN

New PIN hashed again before saving

✔ Check Balance

Always shows the latest, real balance from database

✔ Exit

Safely exit to login screen

📜 Full Transaction History

Every deposit and withdrawal is recorded in the database.

Users can review:

Amount

Type of transaction

Date & time

Card number used

This builds a complete financial history for each card.

🧱 3. Architecture & Technology
Built with:

Java 17+

JavaFX (modern responsive UI)

JDBC / PostgreSQL / Local DB

SHA hashing for PIN protection

MVC-inspired structure for clarity

Full validation layer to prevent invalid or harmful input

Main Components
Component	Description
GUI (JavaFX)	User interactions, ATM screens
Service Layer	Business logic for cards, transactions, validation
Database Layer	Stores all users, PINs, balances, history
Security Layer	SHA hashing, login attempts, validation checks
Validation System	Protects against harmful/incorrect input

🔐 4. Security Highlights
✔ PINs are never stored in plain text

Every PIN is hashed using SHA before insertion into the database.

✔ Input validation everywhere

All fields are validated:

Prevent SQL injection

Prevent invalid/incorrect values

Ensure safe, correct data entry

Prevent attackers from abusing fields

✔ Login attempt tracking

Incorrect PIN tries are counted and restricted (like real ATM logic).

🗃️ 5. Database Features

The database stores:

User personal information

Card details (number, type, PIN hash)

Balance for each card

Full history of all transactions

Every time something happens (withdraw, deposit, change PIN),
a database entry is added.

If transcation fails , there is backup operation which prevents clients 
from losing their withdraw or deposit amount.

🧪 6. Testing

There are 200+ real transaction records used during testing to ensure:

Stability

Correct balance calculations

Accurate history

Error handling

Proper validation

UI performance
