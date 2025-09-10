INSERT INTO filter.filter (name, filter)
VALUES ('Segment filter',
        '[
           {
             "criteria": "AMOUNT",
             "operator": "GREATER_THAN",
             "value": "100.00"
           }, {
             "criteria": "AMOUNT",
             "operator": "LESS_THAN",
             "value": "1250.00"
           }
         ]');

INSERT INTO filter.filter (name, filter)
VALUES ('Profanity filter',
        '[
           {
             "criteria": "TITLE",
             "operator": "CONTAINS",
             "value": "BadWord"
           }
         ]');

INSERT INTO filter.filter (name, filter)
VALUES ('Christmas filter',
        '[
           {
             "criteria": "DATE",
             "operator": "EQUALS",
             "value": "2025-12-24"
           }
         ]');

