# SimpleFunctionsWithMongodb 

Android app : simple functions using monogdb database like  Authentication, InsertData, Update, Find, Delete.


## insertOne();

 ```
 long number = 76808765;
        mongoCollection.insertOne(new Document("_id",user1.getId())
                .append("gender","male").append("userid","0001")
                .append("email","sai123gopal@gmail.com")
                .append("Reg no","18mis7013")
                .append("name","sai gopal")
                .append("phone num",number)
                .append("created at",new Date())
        ).getAsync(new App.Callback<InsertOneResult>() {
            @Override
            public void onResult(App.Result<InsertOneResult> result) {
                if (result.isSuccess()){
                    Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    Log.v("EXAMPLE", "successfully inserted documents.");
                }else {
                    Toast.makeText(MainActivity.this, ""+result.getError(), Toast.LENGTH_SHORT).show();
                    Log.e("write :",result.getError().toString());
                }
            }
        });
```
        

## update();
```
  Document queryFilter = new Document("userid", "0001");
        Document updateDocument = new Document("$set", new Document("name", "updated sai"));
        mongoCollection.updateMany(queryFilter, updateDocument).getAsync(task -> {
            if (task.isSuccess()) {
                long count = task.get().getModifiedCount();
                if (count != 0) {
                    Log.v("EXAMPLE", "successfully updated " + count + " documents.");
                } else {
                    Log.v("EXAMPLE", "did not update any documents.");
                }
            } else {
                Log.e("EXAMPLE", "failed to update documents with: ", task.getError());
            }
        });

```

## delete();
```
 Document queryFilter = new Document("userid", "0001");
        mongoCollection.deleteOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                long count = task.get().getDeletedCount();
                if (count == 1) {
                    Log.v("EXAMPLE", "successfully deleted a document.");
                } else {
                    Log.v("EXAMPLE", "did not delete a document.");
                }
            } else {
                Log.e("EXAMPLE", "failed to delete document with: ", task.getError());
            }
        });
```
## find();
```
 Document queryFilter  = new Document("userid", "0001");
        mongoCollection.findOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                Log.v("EXAMPLE", "successfully found a document: " + task.get().toString());
                Toast.makeText(this, ""+task.get().toString(), Toast.LENGTH_SHORT).show();
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
            }
        });
```
