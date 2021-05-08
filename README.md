YAPILY Requirements

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
            - Retry 3 times if Marvel doesn't reply
            - Catch Exception if no Ids found
            - Catch other server error exceptions.

------------------------------------------------------------------------------------------------------------------------

Database design definition
Character
    id
    name
    description
    Thumbnail (@OneToMany)

Thumbnail
    path
    extension

------------------------------------------------------------------------------------------------------------------------
