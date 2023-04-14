# Team 30 Organization 

## Summary of what each team member did and how the team was organised.

All members participated about the same in everything - most or all changes were discussed between everyone in external communication channels, so all members contributed to the design decisions of all aspects regardless of who implemented them.

- Jack Boakes - jboa832 - TheBoakey - performers and users (domain model, mappers and resource methods), important changes to all mapper classes and overall code refactoring, contribution to design decision discussion

- Liam Douglas - ldou933 - NachoToast - bookings and seats (domain model, mappers and resource methods), important changes to domain models, bug fixes and code refactoring, contribution to design decision discussion

- Hadas Livne - hliv559 - designateddolt - concerts (+dates), login and subscriptions (domain model, mappers and resource methods), project overall planning, code refactoring, contribution to design decision discussion


## Short description of the strategy used to minimise the chance of concurrency errors in program execution
Subscriptions are implemented using ConcurrentLinkedQueue and ConcurrentHashMap to prevent concurrency errors with subscribing and posting notifications.

## Short description of how the domain model is organised