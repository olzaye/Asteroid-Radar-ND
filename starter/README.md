# Purpose of this Folder

It's a project which is more to learn about

 - Retrofit: Fetching data from Network 
 - Room: using Database to save data that we fetch from Network and it will be listed in a recyclerview.
 - WorkManager: I used here wrokmanager to fetch data when the device is in background and with some preferences that I defined for the work request.
 
 !! Please if want to build the project on your device you should do like it is defined below:
     1. Create an api key from the Nasa open APIs website  https://api.nasa.gov/  
     2. After created the api_key, put it directly to the Constants class where the property called "API_KEY"

      object Constants {

         ...

          const val API_KEY = "YOUR_API_KEY"

         ...
      }


