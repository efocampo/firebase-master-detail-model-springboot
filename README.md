# firebase-master-detail-model-springboot
Example of a master-detail application based on springboot that uses as a backend google firebase for managing a user with its roles. It was implemented a generic dao for the CRUD operations with the firebase database. It uses pagination as well. I'm using insomia as a rest services client.

### Creating a user
- I'm creating the user without his roles yet. The primary key value is empty, because is given by firebase. You can see the attribute called cod_id_user with the value provided by the database in the response.

![Creating user](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/01_crearusuario.png)

- You can see now, how the user was created on Firebase.

![Creating user firebase](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/01_firebase.png)

- You also can create the user with his roles.

![Creating roles](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/011_userWithRoles.png)

- You can see now, how the user with his roles was created on Firebase.

![Creating roles firebase](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/011_userwithRolesFirebase.png)

### Updating a user
- I'm updating the username and lastname. I'm just adding the word updated at the end of each value.

![Updating user](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/02_userUpdate.png)

### Deleting a user
- For the calling, it's mandatory to specify the primary key value (cod_id_user). 

![Deleting user](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/03_userDelete.png)

### Finding a user by ID (primary key)
- For the calling, it's mandatory to specify the primary key value (cod_id_user) in the variable called documentId. 

![Finding a user by ID](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/04_findById.png)

### Getting all users
- It returns all users. 

![Getting all users](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/04_getAllUsers.png)

### Pagination
- For this example, you will find out that the url is api/user/get/paged/1/2, where 1 means that you are requesting the first page and the number 2 means: only brings two users. 

![Getting paginated](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/05_pagination.png)

### Add new role to user. Relation one to many
- For this example ../api/user/update/rol/db5344be-4fd1-46e1-929e-f207f1f28215/false, you will find out that db5344be-4fd1-46e1-929e-f207f1f28215 is the primary key and /false means if you want to delete a role. Thus, I'm reusing this calling to add and remove roles to an user.

![add new role](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/06_addNewRoleToUser.png)

- You can see the effects on firebase as follows:

![add new role firebase](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/06_addNewRoleToUSerFirebase.png)

### Remove a role from an user. 
- For this example ../api/user/update/rol/db5344be-4fd1-46e1-929e-f207f1f28215/true, you will find out that db5344be-4fd1-46e1-929e-f207f1f28215 is the primary key and /true means that you want to delete a role. Thus I'm reusing this calling to add and remove roles to an user.

![remove role](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/07_deleteRoleFromUser.png)

- You can see the effects on firebase as follows:

![remove  role firebase](https://raw.githubusercontent.com/efocampo/firebase-master-detail-model-springboot/main/imgs/07_deleteRoleFromUserFirebase.png)
