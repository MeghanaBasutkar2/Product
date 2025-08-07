Project Name: Product
Overview
This Java-based backend service uses MongoDB to store and retrieve data. It supports social login authentication via Facebook and Google to authenticate users from frontend requests and save user information in the database. 
The service provides typical REST APIs to fetch products and users, add new products, and includes an API to read product list data from Excel sheets.

Features
MongoDB Integration: Store and retrieve user and product data using MongoDB.

Social Logins: Authenticate users with Facebook and Google OAuth.

User APIs: Fetch user details.

Product APIs: Fetch all products and add new products.

Excel Data Import: API endpoint to read and process product list data from Excel files.

HTTP Method	Endpoint	Description
POST	/api/products/save	Upload and save products from an Excel file
GET	/api/products	Retrieve all products
GET	/api/products/{id}	Retrieve a single product by its ID
POST	/api/auth/login	Authenticate user via OAuth (Google or Facebook)

(Notes: The provider in /api/auth/login must be either "google" or "facebook" in the request body.
The Excel file is uploaded using @ModelAttribute (i.e., form-data) to /api/products/save.)

Setup Instructions
1. Clone the repository:
  git clone <repository-url>
  cd <repository-folder>

2. Build the project with Maven or Gradle:
  ./gradlew build

Project Notes:
Make sure MongoDB instance is running and accessible.
Set up Facebook and Google OAuth apps and provide correct credentials.
Excel file structure must follow the expected format for successful processing.

