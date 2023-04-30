# Team 30 Organization 

## Summary of what each team member did and how the team was organised.

All members participated about the same in everything - most or all changes were discussed between everyone in external communication channels, so all members contributed to the design decisions of all aspects regardless of who implemented them.

- Jack Boakes - jboa832 - TheBoakey - performers and users (domain model, mappers and resource methods), important changes to all mapper classes and overall code refactoring, cascading design changes, concurrency control implementation, contribution to design decision discussion

- Liam Douglas - ldou933 - NachoToast - bookings and seats (domain model, mappers and resource methods), important changes to domain models, bug fixes and code refactoring, code commenting and readability, contribution to design decision discussion

- Hadas Livne - hliv559 - saltlas - concerts, concert dates, login and subscriptions (domain model, mappers and resource methods), project overall planning, code refactoring, client-side testing, contribution to design decision discussion


## Short description of the strategy used to minimise the chance of concurrency errors in program execution
The bookings POST method reads and writes from the database within the same method. If a seat in the BookingRequestDTO is booked, the method throws an exception, preventing the double-booking. Since the method is synchronous, it's highly unlikely that there will ever be a case in which one booking post is reading before another and writing after another, therefore the initial commit should still win. Regardless, we have implemented `PESSIMISTIC_READ` when fetching Seat objects for booking creation, which locks the table in such a way that allows other methods to read it, but not to write to it.

Subscriptions are implemented using ConcurrentLinkedQueue and ConcurrentHashMap to prevent concurrency errors with subscribing and posting notifications. This is especially important as they are asynchronous methods and have higher risks of concurrency issues.

## Short description of how the domain model is organised
### Fetching
Our application uses lazy fetching in almost all places - this is intentional, as there are many relationships in our domain model and it would be very inefficient to eager fetch everything every time we fetched anything. We implemented eager fetching in 4 places:
- Concert.dates

While dynamic fetching may have worked here since the Concert table is also used for concert summaries, which doesn't require dates, we decided the n+1 query problem was a bigger concern here, as for large amounts of concerts, making a trip to the database for each concert to fetch the dates was inefficient. Concert summaries should still be marginally faster than fetching all concerts, as the mappers have less to convert into DTOs.

- Concert.performers

As above, we decided the n+1 query problem was a bigger concern than inefficiency in this case, as to create a list of performers to be sent off via the ConcertDTO, we had to convert each performer into a PerformerDTO as well.

- ConcertDate.seats

Our booking, seats and subscription methods must use ConcertDate.seats (all seats available at a given concert date) to operate correctly. As such, we decided that fetching the seats in a date eagerly was a better idea than fetching them dynamically every time.

- Booking.date

Dates for bookings are stored in ConcertDate objects, which have a property that contains the LocalDateTime date needed in a BookingDTO.

### Cascading
We used cascading (`CascadeType.ALL`) in 3 places:
- Concert.dates

When a concert is deleted, we want all of its dates to be deleted as well

- ConcertDate.seats

When a concert date is deleted, we want all of the seats for that date to be deleted as well

- User.bookings

When a user is deleted, we want all of its bookings to be deleted as well.