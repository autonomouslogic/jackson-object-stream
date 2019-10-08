# Jackson Object Stream

A small library which handles reading and writing a series of JSON objects from a text file.

## Reading a JSON stream

Objects are read from the files one by one.
Any whitespace or formatting will be ignored by Jackson, so there are no special formatting requirements.

```java
JacksonObjectStreamFactory factory = new JacksonObjectStreamFactory(new ObjectMapper());
try (JacksonObjectIterator<User> iterator = factory.createIterator(new File("users.json"), User.class)) {
    for (User user : iterator) {
    	// Use user for something.
    }
}
```

## Writing a JSON stream

Objects will be written to the output one by one.
After each object, a newline will be inserted.
Pretty printing is not supported at this time.

```java
JacksonObjectStreamFactory factory = new JacksonObjectStreamFactory(new ObjectMapper());
try (JacksonObjectStreamWriter writer = factory.createWriter(new File("users.json"))) {
    writer.writeObject(user1);
    writer.writeObject(user2);
    // etc.
}
```
