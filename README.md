# HealthApp - Contributors
### 🔵 Shartaz Yeasar Feeham - Community service, Notification service, Manual Recommendation service
### 🟢 Prato Dewan - User service, Automated Recommendation service 
### 🔵 Abdullah Al Mamun - Mental Health service, GPT service, Data Analysis service
### 🟢 Mehedi Hasan - Nutrition service, Feedback service, Admin dashboard service

<br><hr><hr>

## User service
The following sections describe the planned **database design**, relations between different tables, **required API endpoints and inter-service dependencies**. Information provided here is not fixed. **Include more if needed**. If any API/service requires an internal call to a different service then use this icon 🔴 to mark it as important so that the developer assigned to that particular service can see high priority API easily. 
### Database relations
![alt text](/Resources/DBDiagrams/UserService.png?raw=true)
### Required APIS

- **/users/register**: User registration.
- **/users/login**: User login, returns JWT auth token with user_id, roles and expire time.
- **/users/login/internal**: Return a JWT token with user_id *"INTERNAL"* and role  *"INTERNAL"* expire time should be very long for internal communication. Only admin can access this API.
- **/users/change-password**: Change user password.
- **/users/assign-role**: Assign a role to a user.
- **/users/remove-role**: Remove a role from a user.

- **/users/profile [create/update/read-by-id]**:
    - Create or update user profile information.
    - Read user profile information by ID.

- **/users/contact [create/update/read-by-id]**:
    - Create or update user contact information.
    - Read user contact information by ID.

  - **/users/health-data**: Provide health data (🔴 Internal call to health service) 

<br>*🔴 Add vegetarian info in the profile table*
  <br><hr><hr>


## Nutrition service
The following sections describe the planned **database design**, relations between different tables, **required API endpoints and inter-service dependencies**. Information provided here is not fixed. **Include more if needed**. If any API/service requires an internal call to a different service then use this icon 🔴 to mark it as important so that the developer assigned to that particular service can see high priority API easily.
### Database relations
![alt text](/Resources/DBDiagrams/NutritionService.png?raw=true)
### Required APIS

- **/nutrition/**: Make all 4 CRUD API's - create, read, update, delete.
- **/nutrition/search**: Search for healthy foods. If the searched keyword matches with any food (organic) then return all of its information with recipe list as well, however if there is no recipe then just return the food info. 

- **/nutrition/details/{word}**: Get all details of a food (same as get by name).
- **/nutrition/details/recipe/{id}**: Get recipe nutrition details. 
- **/nutrition/details/food/{id}**: Get food nutrition details.

- **/nutrition/recommendations**: Retrieve food recommendations based on specified parameters.
    - For example, if you specify "Vitamin-B" as the parameter, it will return foods rich in Vitamin-B.
    - If you specify "Protein," it will return high-protein foods.
    - This API can provide recommendations based on various nutritional criteria.

<br><hr><hr>

## Mental Health service
The following sections describe the planned **database design**, relations between different tables, **required API endpoints and inter-service dependencies**. Information provided here is not fixed. **Include more if needed**. If any API/service requires an internal call to a different service then use this icon 🔴 to mark it as important so that the developer assigned to that particular service can see high priority API easily.
### Database relations
![alt text](/Resources/DBDiagrams/MentalHealthService.png?raw=true)
### Required APIS
- **/mental-health/exercises**: Create all 4 CRUD API's for mental health exercise.
- **/mental-health/mood-tracking**:  Create all 4 CRUD API's for mood data. Use mood enum of at least 5 or more mood types.



<br><hr><hr>

## Community & Social service
The following sections describe the planned **database design**, relations between different tables, **required API endpoints and inter-service dependencies**. Information provided here is not fixed. **Include more if needed**. If any API/service requires an internal call to a different service then use this icon 🔴 to mark it as important so that the developer assigned to that particular service can see high priority API easily.
### Database relations
![alt text](/Resources/DBDiagrams/CommunityService.png?raw=true)
### Required APIS
- **/community/groups**: Create all 4 CRUD API's for group.
- **/community/groups/add-member**: Add new member to the group with role.
- **/community/groups/remove-member**: Remove member from group.
  <br><br>
- **/community/posts**:  Create all 4 CRUD API's for posts. 
- - If its a group post, set privacy to *"GROUP"* and provide group_id otherwise privacy should be public/private and group_id should be null
- - Name of user should be attempted to be parsed from User service during post creation and get post services. However, in case of fail, go with leaving it *INACCESSIBLE USER*.
- **/community/interactions/{post_id}/add-like**: Add a new like
- **/community/interactions/{post_id}/add-dislike**: Add a new dislike
  <br><br>
- **/community/posts/comments**: Create all 4 CRUD API's for comments'
- **/community/posts/comments/interactions/{comment_id}/add-like**: Add like to a comment
- **/community/posts/comments/interactions/{comment_id}/remove-like**: Remove like from a comment
  <br><br>
- **/community/achievements**: Create all 4 CRUD API's for achievements
- **/community/achievements/add-progress/{user_id}/{achievement_id}/{score}**: Update an achievement score for a user
- **/community/achievements/by-user/{user_id}**: Give a list of all achievement progresses by an user.  

<br><hr><hr>