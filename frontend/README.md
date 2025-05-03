# PantryPal

## Setup Instructions

### Backend
1. Make sure MySQL is running on port 33306.
2. In MySQL, run the `ddl.sql` script (located in the `backend` folder) to create tables.
3. Open a terminal in the `backend` folder.
4. Run the app using:
   ./mvnw spring-boot:run

### Frontend
1. Open a separate terminal in the `frontend` folder.
2. Run the following (only the first time):
   npm install

3. Start the frontend:
   npm start

## Completed Features
- User signup and login (with encrypted passwords)
- Create and view recipes
- View recipe details with title, instructions, creator, and timestamp
- Frontend-backend integration
- Basic navigation bar and styling

## In Progress / To Do
- Save and unsave recipes
- Comment system
- User following
- Track logged-in user session
- Polish layout and CSS
