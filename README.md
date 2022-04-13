## Transaction service

### Requirements

- Java 17 (when working with multiple jdks, [jenv](https://developer.bring.com/blog/configuring-jenv-the-right-way/) can
  be used)
- Docker

### CLI Application

Can be run with `./cli/init.sh`. Prints transaction data enriched by sum of cost of transaction's products on standard
output.

### Web Application

Can be run with `./web/init.sh`. After that http server is available at `http://localhost:8080`. It exposes two
endpoints:

- POST `/enrich/transaction`

```json 
{
    "id": 0,
    "saleDate": "2012-12-12",
    "products": [
        {
            "name": "Name",
            "cost": "12.80"
        }
      ]
}
```

Returns transaction with cost of all products

```json
{
    "transaction": {
        "id": 0,
        "saleDate": "2012-12-12",
        "products": [
            {
                "name": "Name",
                "cost": 12.80
            }
        ]
    },
    "cost": 12.80
}
```

- POST `/enrich/transactions`

```json 
[
    {
    "id": 0,
    "saleDate": "2012-12-12",
    "products": [
        {
            "name": "Name",
            "cost": "12.80"
        }
      ]
    },
    {
    "id": 0,
    "saleDate": "2012-12-12",
    "products": [
        {
            "name": "Name",
            "cost": "12.80"
        }
      ]
    }
]
```

Returns transactions with costs of all products

```json
[
    {
        "transaction": {
            "id": 0,
            "saleDate": "2012-12-12",
            "products": [
                {
                    "name": "Name",
                    "cost": 12.80
                }
            ]
        },
        "cost": 12.80
    },
    {
        "transaction": {
            "id": 0,
            "saleDate": "2012-12-12",
            "products": [
                {
                    "name": "Name",
                    "cost": 12.80
                }
            ]
        },
        "cost": 12.80
    }
]
```