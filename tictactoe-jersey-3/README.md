
It is just interesting behavior that I have accidentally found.
If we will use @Produces("application/json")
and don't use @Broadcast annotation
and don't do transformation to json by yourself then
Jersey will do it fo us.

So, the questions:
1. Is it bug?
2. Can we use JSON support powered by Jersey?