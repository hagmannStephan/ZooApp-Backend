db = db.getSiblingDB('zoo_RestAPI');
db.createCollection('tickets');
db.tickets.insert([
    {
        "id": 0,
        "date": "1-1-2024",
        "numAdults": 1,
        "numChildren": 1,
        "price": 22.5
    },
    {
        "id": 1,
        "date": "1-2-2024",
        "numAdults": 2,
        "numChildren": 1,
        "price": 37.5,
    },
    {
        "id": 2,
        "date": "1-11-2024",
        "numAdults": 2,
        "numChildren": 1,
        "price": 37.5
    },
    {
        "id": 3,
        "date": "2-13-2024",
        "numAdults": 2,
        "numChildren": 3,
        "price": 52.5
    }
]);