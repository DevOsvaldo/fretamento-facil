# Back-End Fretamento Fácil

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)


Este projeto é uma API construída usando Java, Java Spring, PostgresSQL como banco de dados, e Spring Security e JWT para controle de autenticação.



## Indice

- [Instalação](#instalação)
- [Configuração](#configuration)
- [Modo de Uso](#usage)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [Database](#database)
- [Contributing](#contributing)

## Instalação

1. Clone the repository:

```bash
git clone https://github.com/DevOsvaldo/fretefacil
```

2.Instale dependências com Maven

3. Instale [PostgresSQL](https://www.postgresql.org/)

## Usage

1. Inicie o aplicativo com Maven
2. A API estará acessível em http://localhost:8080


## API Endpoints
A API fornece os seguintes endpoints:

```markdown
GET /cargas/{id} - Retrieve a list of all products. (all authenticated users)

POST /cargas - Register a new product (ADMIN access required).

POST /auth/login - Login into the App

POST /auth/register - Register a new user into the App
```

## Authentication
A API usa Spring Security para controle de autenticação. As seguintes funções estão disponíveis:

```
USER -> Standard user role for logged-in users.
ADMIN -> Admin role for managing partners (registering new partners).
```
Para acessar endpoints protegidos como usuário ADMIN, forneça as credenciais de autenticação apropriadas no cabeçalho da solicitação.

## Database
O projeto utiliza [PostgresSQL](https://www.postgresql.org/) como banco de dados.

## Contributing

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões de melhorias, abra um problema ou envie uma solicitação pull ao repositório.






