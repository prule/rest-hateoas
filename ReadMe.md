
# Sample Spring HATEOAS Application - Using Ports and Adapters

This project provides a sample application that demonstrates how to use [Spring HATEOAS](https://spring.io/projects/spring-hateoas) to implement hypermedia-driven RESTful APIs using the Ports and Adapters pattern.

See branch [version1](https://github.com/prule/rest-hateoas/tree/version1) for a simple layered architecture version of this application.

## Capabilities

* Authentication and Authorization with Spring Security and JWT
* Validation
* Conflict detection via hibernate versioning


## To do

* Use cookies for the JWT token https://medium.com/spring-boot/cookie-based-jwt-authentication-with-spring-security-756f70664673
* Application shouldn't depend on spring. Need to map the Page and Pageable stuff to domain.

## Enhancements

* In order to make P&A more sensible the application will need to be more complex - and have relevant business logic
  * Perhaps a library management system
    * Multi-tenanted to support many libraries with their own staff
      * Admin 
        * can create a library
        * add/remove staff to library
      * Staff
        * can update library details
        * update location of book
        * can add books to the library
        * can add users to the library
        * can manage books, authors, and users
      * Users
        * A user can check a copy of a book out
        * A user can check a copy of a book in
        * A copy of a book can be checked out by one user at a time
        * A user can leave a review of a book
        * A user can reserve a book if all copies are checked out
        * A user must renew their membership every year
        * A users membership must be current to check out a book
        * A user can have only 1 membership
        * A user can only check out N books at a time
        * A user should be notified when a book they have reserved is available
        * A user should be notified when a book they have checked out is reserved
        * A user should be notified when a book they have checked out is overdue
        * A user should be able to renew a book they have checked out
        * Users can search for books
      * An Author can have many Books
         * A Book can have many Authors
         * A Book can have many Reviews
         * There can be multiple copies of a book
         * There can be multiple reservations for a book
         * A book can have multiple types (paper, ebook, audiobook)


Notes

* If you see this error: `Could not find a valid Docker environment. Please check configuration.` while running tests, you need to have docker running (so test containers
  can be created).
* https://medium.com/@flbenz/kotlin-and-domain-driven-design-value-objects-fb8dd1e7d965
* https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/value-classes.md
* 