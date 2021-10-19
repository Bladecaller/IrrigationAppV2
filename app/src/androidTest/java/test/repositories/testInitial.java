package test.repositories;


//import android.support.test.InstrumentationRegistry;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import model.room.repositories.AccountRepo;

public class testInitial {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();


    AccountRepo repository;

    @Before
    public void setUp() throws Exception {
        //Context context = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        repository = new AccountRepo();

    }

    @Test
    public void populateAccCustomerAPI() throws InterruptedException {
        repository.getTemp("metObs/collections/observation/items?stationId=06102&parameterId=temp_dry&period=latest&api-key=14cc5e73-90a5-463b-a221-68e503b2a396");


    }
}