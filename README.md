# webapp

## Cloud Native Web Application

## Getting Started

### Prerequisites

- [Java](https://www.java.com/) installed on your machine
- [Maven](https://maven.apache.org/) installed on your machine

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/cloudapp6225/webapp.git

2. Install postgresql

    ```bash
    sudo apt install -y postgresql postgresql-contrib

3. Navigate to the project

   ```bash
   cd webapp/

4. Build project

    ```bash
    mvn clean install

5.  Run Locally

    ```bash
    mvn spring-boot:run

## Development

### User Schema

```
{
    id:	string($uuid)
    first_name: string
    last_name: string
    password: string($password)
    username: string($email)
    account_created: string($date-time)
    account_updated: string($date-time)
}
```
### Testing

- [Postman client](https://www.postman.com/) install on your machine

### API Endpoints

### `POST http://localhost:8080/v1/user`
Create a new User
```
{
  "first_name": "Jane",
  "last_name": "Doe",
  "password": "skdjfhskdfjhg",
  "username": "jane.doe@example.com"
}
```
### Authenticated Endpoints
### `GET http://localhost:8080/v1/user/self`
Get a user by passing username and password in basic authentication header.

### `PUT http://localhost:8080/v1/user/self`
Update user details by providing authentication headers.
svdd

## License

MIT License

Copyright (c) 2024 cloudapp6225

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
