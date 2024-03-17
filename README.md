# Documentation

After running the project you can access to SwaggerUI for API endpoints. 
    
    http://{URL}:{PORT_NUMBER}/swagger-ui/index.html
Examples:

    http://localhost:8081/swagger-ui/index.html (User Service)
    http://localhost:8080/swagger-ui/index.html (Restaurant Service)

# To Run The Project

Cloning The Project
---------------------
    git clone https://github.com/yagizpancak/n11-bootcamp-final.git

Running Containers
---------------------
To run project locally

    docker-compose up -d

To stop

    docker-compose down

It will run in the 8080 port. You can send the requests to http://localhost:8080/api/v1/cart url.

If you are facing with this error. Please make sure that mwnw file have line seperator LF.

    /bin/sh: 1: ./mvnw: not found


