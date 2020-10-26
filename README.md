# MyApplication
Spring boot simple app

The db-connector (localhost:8080) is responsible for connection to the database and provides methods (like create, update, delete and get an user) to operate on the database.

The webApp connects to the db-connector through REST and provides db-connector funcionality by a browser.

When we get to the website (localhost:8081) we have limited access to subpages.
We can sign up and sign in to gain (if the account has moderator or admin role) an access to other functionality like search user by id or login and create user (admin only).
