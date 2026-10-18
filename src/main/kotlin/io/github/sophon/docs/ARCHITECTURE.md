# ARCHITECTURE

This project uses a modified **Hexagonal + DDD architecture** (`hexDDD`), inspired by **Tom Homberg's Getting Your Hands Dirty With Clean Architecture** (`book`).

### Hexagonal architecture

Strict adherence, see [hexDDD.mmd](hexDDD.mmd).

### Packaging

Modified packaging - the priority was to have packages in a flat hierarchy, for faster navigation.

As such, there are differences from the book:

- `ports/in` moved to the root as a single file, `InboundPorts.kt`, so every entry into the app is in one place
- `app/ports/out` renamed and moved to `app/outPort`
- `app/domain` moved to `app/`
- `app/domain/model` split to public `model/` and internal `app/model`
- `adapter/in` and `adapter/out` renamed to `adapter/inbound` and `adapter/outbound`, with one package per technology

Only the public parts below may be public. Everything in `app/` and `adapter/` is `internal`.

```
io.github.sophon/
├── Application.kt           (public)
├── InboundPorts.kt          (public)  - use case interfaces
├── model/                   (public)  - domain model exposed to the outside world
├── app/                     (internal)
│   ├── model/                         - internal domain model
│   ├── outPort/                       - ports implemented by outbound adapters
│   ├── service/                       - use case implementations
│   └── util/
├── adapter/                 (internal)
│   ├── inbound/
│   │   └── http/                      - REST controllers
│   └── outbound/
│       ├── postgres/                  - PostgreSQL + pgvector
│       ├── bgg/                       - BoardGameGeek XML API
│       └── openai/                    - OpenAI embeddings
└── docs/                    (public)
```

### Testing

