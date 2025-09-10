# Filter App

## Description :

Simple CRUD application for filters.

## Configurations :

Backend :

- language: Java-17
- port: 8000
- directory: server

Frontend:

- framework: React (TypeScript)
- port: 5173
- directory: client

## Prerequisites :

1. Docker [install](https://docs.docker.com/engine/install/)
2. Docker-compose  [install](https://docs.docker.com/compose/install/)

## Usage:

Quick Start: Open a terminal in the project's root directory and run `./start.sh`
Command will create necessary containers for application to run.

## API ENDPOINTS:

All endpoints are prefixed with `/api`.

Running the application locally, the base URL will be: `http://localhost:8000/api`

> **[GET]:** `/api/filters` = Returns all allowed criteria and operators

```
Response[status=200]:
{
    "systemFilters": {
        "DATE": [
            "BEFORE",
            "AFTER",
            "EQUALS"
        ],
        "AMOUNT": [
            "GREATER_THAN",
            "LESS_THAN",
            "EQUAL_TO"
        ],
        "TITLE": [
            "EQUALS",
            "CONTAINS",
            "STARTS_WITH",
            "ENDS_WITH"
        ]
    }
}
```

> **[GET]:** `/api` = Returns List of Client Filters

```
Response[status=200]:
[
    {
        "id": 1,
        "name": "Profanity filter",
        "filterList": [
            {
                "criteria": "TITLE",
                "operator": "CONTAINS",
                "value": "BadWord"
            }
        ]
    }
]
```

> **[POST]:** `/api` = Creates new record when no id is provided, otherwise updates existing record.

```
Example:
Request Body:
[
    {
        "name": "Profanity filter",
        "filterList": [
            {
                "criteria": "TITLE",
                "operator": "CONTAINS",
                "value": "BadWord"
            }
        ]
    }
]

Response[status=200]:
[
    {
        "id": 1,
        "name": "Profanity filter",
        "filterList": [
            {
                "criteria": "TITLE",
                "operator": "CONTAINS",
                "value": "BadWord"
            }
        ]
    }
]
```

> **[DELETE]:** `/api/delete/{id}` = Delete record based on filters id

```
Request:
/api/delete/1

Response [status=200]:
"Filter has been deleted."
```
