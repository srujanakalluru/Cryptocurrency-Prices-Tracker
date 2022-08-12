# Cryptocurrency-Prices-Tracker
A REST API app that continuously monitors the price of Bitcoin using a third-party API and alerts a given email when the price either goes above or below given limits.


## Goal
1. Build a REST API app that continuously monitors the price of Bitcoin using a third-party API
   - Alerts a given email when the price either goes above or below given limits
   - Stores the queried price in the database
2. Create the endpoint `/api/prices/btc?date=DD-MM-YYYY` that accepts the below request params
   - `data` in the format `DD-MM-YYYY` (Mandatory param)
   - `limit` - for pagination that indicates how many rows that need to be returned 
   - `offset` - for pagination that indicates what’s the offset entry from where the rows need to start  

### Additional requirements
- Should allow the user to define a max and min price in USD in the environment variables such that when the price crosses the below min or above the max values, the app sends an alert email to the defined email in the environment variables.
- The end point `/api/prices/btc?date=DD-MM-YYYY` must allow the developer to query the database with the below expected output
  ```json
  {
    "url": "<http://localhost:8000/api/prices/btc?date=29-03-2022&offset=0&limit=100>",
    # current url

    "next": "<http://localhost:8000/api/prices/btc?date=29-03-2022&offset=100&limit=100>",
    # next url in pagination

    "count": 2880,
    # total no of records for the given query

    "data": [{
            "timestamp": "<UTC Time>"
            "price": < Integer > ,
            "coin": "btc"
        }, # price object with a timestamp
        ...
    ],
    # array of price objects
  }
3. Use Mailtrap SMTP service to send alert emails. Be sure to include Mailtrap config variables in the .env environment variable file.
4. Dockerize the project

## References
- CoinGecko API Doc: https://www.coingecko.com/en/api/documentation
- Mailtrap: https://mailtrap.io/
