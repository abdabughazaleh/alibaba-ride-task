# Scalable Ride-Sharing
Task link: [Click Here](https://quilted-mollusk-ed7.notion.site/Hiring-Test-Development-expert-197267b86181808fa9b6dc9b9c359bdf)


# What I do

I created 4 microservices
 - Api-Gateway
 - Auth-Service
 - Ride-service
 - Driver-Service


## How to run
Create docker netwrok using this command : `docker network create app-network`
 - Run RabbitMQ docker-compose.yaml file by the following:
	 - Go to this dircitory `rabbitmq-config` then run this command `docker-compose up` to make sure it's working fine check this url : http://localhost:15672/.
	 - Now after running RabbitMQ server, we need to change password by command line : `docker exec -it rabbitmq rabbitmqctl change_password admin admin`.  (I added this extra steps because when I added configuration to generate queues and topics for RabbitMQ, not working, please note password and username must be (admin admin) )
	 - Now we need to back to main folder `scalable-ride-sharing` and run this command line `docker-compose up`
	 - Now go to http://localhost:15672/ and sign in (username :admin, password: admin).
	 - Now we ready to testing

## How to test

 1. Go to postman and import file `Ride-Sharing.postman_collection.json` located in main folder.
 2. Then create new driver and rider account by SignUp request in each one (to make testing easy ). 
 3. Login as driver and update status to (ONLINE). avaiable status (ONLINE, OFFLINE) (the driver must be online to receive rides). 
 4. Now login by auth request, the token will stored by default in envirotment variable, if you not located for better testing add it.
 5. Now login with rider and send ride request you will get a tranNo, and will located in driver notifications.
 6. Rider can cancel order by sinding tranNo.
 7. Now login in driver account, and accept ride the status will be changed to (IN_PROGRESS).
 8. After complete ride, send complete request.
 9. Now login as rider and rate ride. 
 

## [IMPORTANT] Justification

First of all,
I tried hard to do what I could in two days and I actually worked on Friday (18 continuous hours) and Saturday (15 continuous hours) to finish this task, and the reason I worked in two days is that I was in Jordan before I received the task from you and for your information I took two days off (Wednesday and Thursday) before February 19 and February 20 of last month to finish the task but unfortunately the task was delayed, after that I traveled.

Why am I telling you this, the reason is that I did not finish everything required...
- I did not finish the followings:
	- vehicle images.
	- Enable drivers to upload or update verification documents (e.g., driver’s license, vehicle registration).
	- Ensure data replication between the primary and replica.

I hope you accept my apology and thank you. If you want me to contiune contact with me I will do my best to do it.
