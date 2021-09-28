package com.vitap.sai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.bson.Document;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import javax.sql.StatementEvent;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.result.InsertOneResult;

public class MainActivity extends AppCompatActivity {

    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    User user1;
    MongoCollection<Document> mongoCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        App app =new App(new AppConfiguration.Builder("Your app id here").build());

        String email = "@email.com";
        String pass = "password";

        Credentials anonymousCredentials = Credentials.emailPassword(email,pass);

        AtomicReference<User> user = new AtomicReference<User>();
        app.loginAsync(anonymousCredentials, it -> {
            if (it.isSuccess()) {

                Log.v("AUTH", "Successfully authenticated anonymously.");
                user.set(app.currentUser());

                user1 = app.currentUser();
                mongoClient = user1.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("Database name");
                mongoCollection = mongoDatabase.getCollection("users");


                //insertOne();
                //update();
                //delete();
                //find();

            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });

    }


    public void insertOne(){

        long number = 76808765;

        mongoCollection.insertOne(new Document("_id",user1.getId())
                .append("gender","male").append("userid","0001")
                .append("email","email")
                .append("name","sai gopal")
                .append("phone num",number)
                .append("created at",new Date())
        ).getAsync(result -> {
            if (result.isSuccess()){
                Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                Log.v("EXAMPLE", "successfully inserted documents.");
            }else {
                Toast.makeText(MainActivity.this, ""+result.getError(), Toast.LENGTH_SHORT).show();
                Log.e("write :",result.getError().toString());
            }
        });
    }

    public void update(){
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

    }

    public void delete(){
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
    }

    public void find(){
        Document queryFilter  = new Document("userid", "0001");
        mongoCollection.findOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                Log.v("EXAMPLE", "successfully found a document: " + task.get().toString());
                Toast.makeText(this, ""+task.get().toString(), Toast.LENGTH_SHORT).show();
            } else {
                Log.e("EXAMPLE", "failed to find document with: ", task.getError());
            }
        });
    }
}
