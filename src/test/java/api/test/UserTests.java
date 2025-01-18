package api.test;

import api.endpoints.UserEndPoint;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests {
    Faker faker;
    User userPayload;
    @BeforeClass
    public void setupData()
    {
        faker=new Faker();
        userPayload= new User();
        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setLastname(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());

    }
@Test(priority = 1)
    public void testPostUser()
    {
        Response response = UserEndPoint.createUser(userPayload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(),200);
    }
    @Test(priority = 2)
    public void testGetUserbyName()
    {
        Response response=UserEndPoint.readUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);

    }

    @Test(priority = 3)
    public void testUpdateUserByName()
    {
        userPayload.setLastname(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        Response response = UserEndPoint.updateUser(this.userPayload.getUsername(),userPayload);
        response.then().log().body().statusCode(200);

        //check data after update
        Response responseafterupdate=UserEndPoint.readUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(responseafterupdate.getStatusCode(),200);

    }
    @Test(priority = 4)
    public void testDeleteUserByName()
    {
        Response response=UserEndPoint.deleteUser(this.userPayload.getUsername());
        Assert.assertEquals(response.getStatusCode(),200);
    }
}
