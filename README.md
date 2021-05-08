### Marvel-API

Prerequisites
    - Update path in CharactersGenerator.java e.g. "C:\\api\\marvel\\src\\main\\resources\\characters\\"
    - Update FILE_UPLOAD_DIRECTORY in application.properties
    - Run CharactersGenerator.java
    - We run this to fetch all the characters' ids available
    - We store this data in this folder src/resources/characters
    - This acts like a database/memory/file system, which helps to provide data for our endpoint 1.

How to run this application?
    - Run MarvelApplication.java
    - Tomcat 8080 will be exposed to use the REST URls
    - Either you can Swagger or Postman or IntelliJ build in Rest call or any other tools
        - Swagger URL: http://localhost:8080/swagger-ui.html

How to use this application?
    - Two GET endpoints will be exposed
    - Endpoint 1: localhost:8080/characters
    - Endpoint 2: localhost:8080/characters/{characterId} e.g. localhost:8080/characters/1009144
    - Detail information about these endpoints are given below.

------------------------------------------------------------------------------------------------------------------------

Endpoint 1
    GET /characters

Problem statement
    - Return only the IDs in JSON array of numbers
    - Load all of them beforehand with your application and cache it in memory of file

High level design
    - Service that you can trigger to fetches all the characters and store in a file/memory
    - When fetching, only fetch the ids if possible
    - User uses the endpoint to read the file/memory and returns JSON array of numbers

------------------------------------------------------------------------------------------------------------------------

Endpoint 2
    GET /characters/{characterId}

Problem statement
    - Real time data from Marvel API
    - Only id, name, description, thumbnail

High level design
    - User provides the marvelId
    - Real time call to Marvel API
    - Handle
            - Catch Exception if no Ids found
            - Catch other server error exceptions.

------------------------------------------------------------------------------------------------------------------------

Database design definition
Character
    id
    name
    description
    Thumbnail

Thumbnail
    path
    extension

------------------------------------------------------------------------------------------------------------------------
