# Spring Validation

A simple example of bean validation at different levels ( see below ), and how to convert them into a common JSON format

* Variables
* Class

## TODO
* Improve this README
* Show different group level validation, current it only has 1 group

## Validation Examples

* Variables, meaning annotations on each variable, unable to involve other variables values

Request
```json
{
    "wheels": 0,
    "doors": 100,
    "make": "Ford",
    "model": "Focus"
}
```

Response

```json
{
    "uuid": "21554f70-4a82-4658-a41f-168025e11cd9",
    "path": "/vehicle",
    "status": 400,
    "message": null,
    "type": "REQUEST_VALIDATION_ERROR",
    "typeDescription": "The request is not valid",
    "errors": [
        {
            "defaultMessage": "must be greater than or equal to 2",
            "field": "wheels",
            "rejectedValue": "0"
        },
        {
            "defaultMessage": "must be between 2 and 5",
            "field": "doors",
            "rejectedValue": "100"
        }
    ]
}
```

* Class level, meaning it validates across variables

Request
```json
{
    "wheels": 4,
    "doors": 2,
    "make": "Ford",
    "model": "Focus"
}
```
Response
```json
{
    "uuid": "c776c652-1c13-4719-9338-40d24e94004b",
    "path": "/vehicle",
    "status": 400,
    "message": null,
    "type": "REQUEST_VALIDATION_ERROR",
    "typeDescription": "The request is not valid",
    "errors": [
        {
            "defaultMessage": "make is not a valid 2 door vehicle",
            "field": "make",
            "rejectedValue": "Ford"
        }
    ]
}
```
