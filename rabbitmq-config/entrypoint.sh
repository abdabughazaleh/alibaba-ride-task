#!/bin/bash
# Change the password for the admin user
rabbitmqctl change_password admin admin

# Start RabbitMQ server
exec rabbitmq-server