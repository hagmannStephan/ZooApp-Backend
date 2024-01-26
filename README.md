# Zoo-Backend
## Setup Zoo-Backen
1. Start Docker Desktop
2. Run `docker compose up` in the current directory (A MongoDB container should be created with mock data already inserted into it)
3. Optional: Open the project in your prefered Editor
4. Run the Project

## Test functionality
The file `ZooH Endpoints - Test.postman_collection.json` contains a Postman-Collection with queries for every endpoint. It also has a subforder with bad queries to test the error-handling.

## Important
**If the application does not seem to be working restart the DB-Container and wait a few minutes. It may be that the data has not finished importing.**