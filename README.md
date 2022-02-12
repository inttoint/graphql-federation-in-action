## Apollo Federation subgraph specification

- [Referencing entities](https://www.apollographql.com/docs/federation/entities/#referencing-entities)
- [Extending entities](https://www.apollographql.com/docs/federation/entities/#extending-entities)
---
## Services
- [Game](game/src/main/resources/schema.graphqls)
- [Arena](arena/src/main/resources/schema.graphqls)
- [Player](player/src/main/resources/schema.graphqls)
---
## Example
Query: 
```
{
    arenas {
        id
        name
        description
        games {
            id
            status
            players {
                id
                nickname
                level
            }
        }
    }
}
```

Response:
```
{
  "data": {
    "arenas": [
      {
        "id": "1",
        "name": "Ocean",
        "description": null,
        "games": [
          {
            "id": "2",
            "status": "started",
            "players": [
              {
                "id": "3",
                "nickname": "third",
                "level": 8
              },
              {
                "id": "1",
                "nickname": "first",
                "level": 1
              },
              {
                "id": "2",
                "nickname": "second",
                "level": 5
              }
            ]
          },
          {
            "id": "3",
            "status": "finished",
            "players": [
              {
                "id": "3",
                "nickname": "third",
                "level": 8
              }
            ]
          }
        ]
      },
      {
        "id": "2",
        "name": "Forest",
        "description": "The most wonderful place",
        "games": []
      },
      {
        "id": "3",
        "name": "Town",
        "description": " Large densely populated urban area",
        "games": [
          {
            "id": "1",
            "status": "created",
            "players": [
              {
                "id": "2",
                "nickname": "second",
                "level": 5
              },
              {
                "id": "3",
                "nickname": "third",
                "level": 8
              }
            ]
          }
        ]
      }
    ]
  }
}
```