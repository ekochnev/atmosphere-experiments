
It is just intresting behavior that I have accidentally found.
If we will use @Produces("application/json")
and don't use @Broadcast
and don't do transformation to json by yourself then
Jersey will do it fo us.

So, the question is - can we use it?