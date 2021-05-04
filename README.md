# Fetch Rewards Coding Exercise - Software Engineering - Mobile<br>
Android application for test assignment (Fetch Rewards): https://fetch-hiring.s3.amazonaws.com/mobile.html

Data: https://fetch-hiring.s3.amazonaws.com/hiring.json

TODO:
Display this list of items to the user based on the following requirements: 
1) Display all the items grouped by "listId" <br>
2) Sort the results first by "listId" then by "name" when displaying. <br>
3) Filter out any items where "name" is blank or null.
The final result should be displayed to the user in an easy-to-read list. <br>


Please add these files in your Gradle Scripts > build.gradle (Module: app)

```
dependencies{
...
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
    implementation "androidx.cardview:cardview:1.0.0"
...
}
```

Retrofit: https://square.github.io/retrofit <br>
CardView: https://developer.android.com/guide/topics/ui/layout/cardview

Steps taken within the app to accomplish the TODOs mentioned above:
  1. Fetch data from the endpoint
  2. Create a new list and add only those items where name is not blank or null (TODO #3)
  3. Group Items using a TreeMap (sorts wrt key) in format; 
  ```
  {key: [{Object},..],...}
  ```
  4. For Each element in "Grouped Items", sort the list values (wrt name)
  ```
                  for(Map.Entry<Integer,List<Items>> item : groupedData.entrySet()){
                    Collections.sort(item.getValue(), new Comparator<Items>() {
                        @Override
                        public int compare(Items t1, Items t2) {
                        /// name(format) : Item X
                            return Integer.parseInt(t1.getName().split(" ")[1].trim()) - Integer.parseInt(t2.getName().split(" ")[1].trim());
                        }
                    });
                }
  ```
  <br> For running the app, clone the repository , import the project in Android Studio. Sync all gradle dependencies. Run.
  
  ## Screenshots: <br>
  <img src="https://raw.githubusercontent.com/ritvij-saxena/fetchtest/master/screenshots/Screenshot_1612550502.png" width="250" height="500"/>
  <img src="https://raw.githubusercontent.com/ritvij-saxena/fetchtest/master/screenshots/Screenshot_1612550505.png" width="250" height="500"/>
  
  
  
  Received reject. Possibly due to no architecture pattern used (MVVM,MVC..) and no tests included.
