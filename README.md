# webapp

## Cloud Native Web Application

## Getting Startedx

### Prerequisites

- [Java](https://www.java.com/) installed on your machine
- [Maven](https://maven.apache.org/) installed on your machine

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/cloudapp6225/webapp.git

2. Install postgresql server

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

## Packer Commands


```bash
$ packer init ./packer/.
$ packer fmt ./packer/.
$ packer validate -var 'project_id=dev-gcp-project-1' -var 'source_jar=./target/web-app-0.0.1-SNAPSHOT.jar' -var 'source_env=./.env' -var 'source_systemd=./systemd/webapp.service' ./packer/.
$ packer build -var 'project_id=dev-gcp-project-1' -var 'source_jar=./target/web-app-0.0.1-SNAPSHOT.jar' -var 'source_env=./.env' -var 'source_systemd=./systemd/webapp.service' ./packer/.

