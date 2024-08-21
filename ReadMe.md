> I just discovered the JPA code didn't migrate across from java very well so I'm currently working on cleaning that up and adding functionality.
> 
> So, a lot of this is a work in progress...

# Getting started with Hateoas

This project provides a sample application that demonstrates how to
use [Spring HATEOAS](https://spring.io/projects/spring-hateoas) to implement hypermedia-driven RESTful APIs.

The way I'm using it here is super simple, but it's a good example of how to started and without getting bogged down in all the possibilities that are available.

The key benefits I see here are: 

- The server tells the client what functionality is available by returning links in the
response. 
  - This allows the client to avoid hard-coding the URLs for the various endpoints.
  - The server can control the links returned by whatever logic is appropriate (eg based on state of entities, or permissions of user).
    - The client then knows what actions are available for a resource and can render the UI appropriately.
      - For example, in a search result, is there a next and/or previous link? When rendering a resource, can it be edited?
    - Link presence could be controlled by feature toggles too.
- URLs can be easily changed by backend changes - for example if a v2 api was backwards compatible, it could easily be migrated to.

To bootstap the frontend, we need an index that lists the main starting points - this payload consists of just links, but could have other information such as the version of the backend:

```json
{
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/1/index"
        },
        "greeting": {
            "href": "http://localhost:8080/api/1/greeting{?name}",
            "templated": true
        },
        "persons-search": {
            "href": "http://localhost:8080/api/1/persons"
        },
        "persons-find": {
            "href": "http://localhost:8080/api/1/persons/{key}",
            "templated": true
        },
        "persons-create": {
            "href": "http://localhost:8080/api/1/persons"
        }
    }
}
```
The `persons-find` link lets us retrieve a single person.

If we take the link template
```
http://localhost:8080/api/1/persons/{key}
```
and substitute the key to get
```
http://localhost:8080/api/1/persons/homer
```
we get the response which include data for that person, and links that tell us how to interact with this resource:
```json
{
    "key": "homer",
    "version": 1,
    "name": {
        "firstName": "Homer2",
        "lastName": "Simpson",
        "otherNames": "Jay"
    },
    "address": {
        "line1": "Level 33",
        "line2": "10000 York St",
        "city": "Sydney",
        "state": "NSW",
        "country": "Australia",
        "postcode": "2000"
    },
    "dateOfBirth": "1980-01-01",
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/1/persons/homer"
        },
        "persons-update": {
            "href": "http://localhost:8080/api/1/persons/homer"
        },
        "persons-delete": {
            "href": "http://localhost:8080/api/1/persons/homer"
        }
    }
}
```

Seeing the `persons-search` link in the index, the frontend knows to render the menu item to search for people, and it knows the URL to use to perform the search.

Unfortunately the `persons-search` link isn't templated properly in this example - it should look like the following, in order to convey the optional query string parameters - this is a problem for another day: 
```
http://localhost:8080/api/1/persons{?filter,from,to,page,size,sort}
```

Looking at the response from calling the `person-search` endpoint `/api/1/persons?size=2&page=2&sort=name.lastName,asc&sort=name.firstName,desc` we can see:
- a list of person models (the data) under the `_embedded.personModelList` path
  - each of these has its own links to find, update (edit) and delete
  - we can use this data to render the rows in a search table with appropriate links to view, edit, delete based on the presence of the links
- navigation links for the search result - under `_links`: first, prev, self, next, last
  - we can use this to render navigation elements on the search table such as buttons for first, prev, next, last
- page metadata so we can display how many pages and results

```json
{
  "_embedded": {
    "personModelList": [
      {
        "key": "person2",
        "version": 0,
        "name": {
          "firstName": "person2.firstName",
          "lastName": "person2.lastName",
          "otherNames": "person2.otherNames"
        },
        "address": {
          "line1": "line1",
          "line2": "line2",
          "city": "city",
          "state": "state",
          "country": "country",
          "postcode": "postcode"
        },
        "dateOfBirth": "2024-08-02",
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/1/persons/person2"
          },
          "persons-update": {
            "href": "http://localhost:8080/api/1/persons/person2"
          },
          "persons-delete": {
            "href": "http://localhost:8080/api/1/persons/person2"
          }
        }
      },
      {
        "key": "person3",
        "version": 0,
        "name": {
          "firstName": "person3.firstName",
          "lastName": "person3.lastName",
          "otherNames": "person3.otherNames"
        },
        "address": {
          "line1": "line1",
          "line2": "line2",
          "city": "city",
          "state": "state",
          "country": "country",
          "postcode": "postcode"
        },
        "dateOfBirth": "2024-08-02",
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/1/persons/person3"
          },
          "persons-update": {
            "href": "http://localhost:8080/api/1/persons/person3"
          },
          "persons-delete": {
            "href": "http://localhost:8080/api/1/persons/person3"
          }
        }
      }
    ]
  },
  "_links": {
    "first": {
      "href": "http://localhost:8080/api/1/persons?page=0&size=2&sort=name.lastName,asc&sort=name.firstName,desc"
    },
    "prev": {
      "href": "http://localhost:8080/api/1/persons?page=1&size=2&sort=name.lastName,asc&sort=name.firstName,desc"
    },
    "self": {
      "href": "http://localhost:8080/api/1/persons?page=2&size=2&sort=name.lastName,asc&sort=name.firstName,desc"
    },
    "next": {
      "href": "http://localhost:8080/api/1/persons?page=3&size=2&sort=name.lastName,asc&sort=name.firstName,desc"
    },
    "last": {
      "href": "http://localhost:8080/api/1/persons?page=5&size=2&sort=name.lastName,asc&sort=name.firstName,desc"
    }
  },
  "page": {
    "size": 2,
    "totalElements": 12,
    "totalPages": 6,
    "number": 2
  }
}
```

The search controller endpoint is super simple, with all of the heavy lifting being done by spring-boot, spring-data, and query-dsl:

```kotlin
    // Sort parameters are not templated:
    //    https://github.com/spring-projects/spring-hateoas/issues/706
    //    https://github.com/spring-projects/spring-hateoas/pull/1312
    // At the moment the search href is not templated:
    //    http://localhost:8080/api/1/persons
    // It should be templated:
    //    http://localhost:8080/api/1/persons{?filter,from,to,page,size,sort}
    // But I haven't found a reasonable way to do this.
    @GetMapping
    fun search(
        criteria: PersonSearchCriteria?,
        pageable: Pageable?,
        assembler: PagedResourcesAssembler<Person>?
    ): HttpEntity<PagedModel<PersonModel>> {
        val page = repository.findAll(criteria?.toPredicate() ?: PersonSearchCriteria().toPredicate(), pageable!!)
        return ResponseEntity(
            assembler!!.toModel(page) { person: Person -> personModelAssembler.toModel(person) },
            HttpStatus.OK
        )
    }
```

The logic for how I can search `Person` is encapsulated in `PersonSearchCriteria` and can be used to produce a QueryDSL predicate which is then passed to a spring-data repository. Search endpoints have never been easier!

```kotlin
/**
 * Defines how Person can be searched. The logic here could be anything, but ultimately produces a Query-DSL predicate 
 * which can be passed to a spring-data repository.
 */
class PersonSearchCriteria(
    val filter: String? = null,
    val from: Int? = null,
    val to: Int? = null
) {

    fun toPredicate(): Predicate {
        val qPerson: QPerson = QPerson.person
        val builder = PredicateBuilder()

        builder
            .and(filter) {
                qPerson.name.firstName.containsIgnoreCase(filter)
                    .or(qPerson.name.lastName.containsIgnoreCase(filter))
                    .or(qPerson.name.otherNames.containsIgnoreCase(filter))
            }
            .and(from != null) { qPerson.dateOfBirth.after(LocalDate.of(from!!, 1, 1)) }
            .and(to != null) { qPerson.dateOfBirth.before(LocalDate.of(to!! + 1, 1, 1)) }

        return builder.toPredicate()
    }
}
```

```kotlin
interface PersonRepository : KeyedCrudRepository<Person, Long>, PagingAndSortingRepository<Person?, Long?>, QuerydslPredicateExecutor<Person> {
  fun findOneByKey(key: Key?): Optional<Person>

  override fun findAll(predicate: Predicate, pageable: Pageable): Page<Person>
}
```

I've tried following the spring pattern for Hateoas which means:
- an Entity: Person 
  - JPA entity
- a Model: PersonModel 
  - the object that is serialized to JSON in the response
  - logic here to map from model to entity and entity to model
    - could be extended for more complex logic, enforcing visibility/permissions on fields
  - this part can be simplified by just using `EntityModel.of(entity, links)` instead of creating a class to represent it, but I wanted more control in this example so settled for a simple compromise
- an Assembler: PersonModelAssembler 
  - adds links to the model

Overall it seems so easy to do that it's all pros and little to no cons.

- UI code becomes simpler by just looking for the presence of links
- Logic is centralised and controlled on the server
- It doesn't get in the way

## References

What I've done here only scratches the surface - check out the references for more information:

- https://docs.spring.io/spring-hateoas/docs/current/reference/html/#fundamentals
- https://github.com/spring-projects/spring-hateoas-examples
- https://spring.io/guides/tutorials/rest
- https://dzone.com/articles/bets-practices-of-using-jpa-with-kotlin
- https://reflectoring.io/bean-validation-with-spring-boot/
